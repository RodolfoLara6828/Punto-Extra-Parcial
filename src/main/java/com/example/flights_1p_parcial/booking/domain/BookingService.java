package com.example.flights_1p_parcial.booking.domain;

import com.example.flights_1p_parcial.booking.dto.BookingRequestDto;
import com.example.flights_1p_parcial.booking.dto.BookingResponseDto;
import com.example.flights_1p_parcial.booking.events.BookingConfirmedEvent;
import com.example.flights_1p_parcial.booking.infrastructure.BookingRepository;
import com.example.flights_1p_parcial.exceptions.BadRequestException;
import com.example.flights_1p_parcial.exceptions.ConflictException;
import com.example.flights_1p_parcial.exceptions.ResourceNotFoundException;
import com.example.flights_1p_parcial.flight.domain.Flight;
import com.example.flights_1p_parcial.flight.infrastructure.FlightRepository;
import com.example.flights_1p_parcial.user.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BookingService(BookingRepository bookingRepository, FlightRepository flightRepository,
                          ApplicationEventPublisher eventPublisher) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public BookingResponseDto bookFlight(BookingRequestDto dto) {
        User user = currentUser();

        Flight flight = flightRepository.findById(dto.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id " + dto.getFlightId()));

        if (!flight.getDepartureTime().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("Flight has already departed or is in transit");
        }
        if (flight.getAvailableSeats() <= 0) {
            throw new ConflictException("No seats available on flight " + flight.getFlightNumber());
        }
        if (bookingRepository.existsByCustomerIdAndFlightDepartureTimeBeforeAndFlightArrivalTimeAfter(
                user.getId(), flight.getArrivalTime(), flight.getDepartureTime())) {
            throw new ConflictException("You already have a booking that overlaps with this flight");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Booking booking = new Booking();
        booking.setCustomer(user);
        booking.setFlight(flight);
        booking.setCustomerFirstName(user.getFirstName());
        booking.setCustomerLastName(user.getLastName());
        booking.setBookingDate(LocalDateTime.now());
        Booking saved = bookingRepository.save(booking);

        eventPublisher.publishEvent(new BookingConfirmedEvent(
                saved.getId(),
                saved.getCustomerFirstName(),
                saved.getCustomerLastName(),
                flight.getFlightNumber(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                saved.getBookingDate()
        ));

        return toDto(saved);
    }

    public BookingResponseDto getBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));
        return toDto(booking);
    }

    private User currentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private BookingResponseDto toDto(Booking booking) {
        return new BookingResponseDto(
                booking.getId(),
                booking.getFlight().getId(),
                booking.getFlight().getFlightNumber(),
                booking.getCustomer().getId(),
                booking.getCustomerFirstName(),
                booking.getCustomerLastName(),
                booking.getBookingDate()
        );
    }
}