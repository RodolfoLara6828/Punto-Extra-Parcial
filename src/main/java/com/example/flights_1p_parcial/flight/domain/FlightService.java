package com.example.flights_1p_parcial.flight.domain;

import com.example.flights_1p_parcial.exceptions.BadRequestException;
import com.example.flights_1p_parcial.exceptions.ConflictException;
import com.example.flights_1p_parcial.flight.dto.FlightRequestDto;
import com.example.flights_1p_parcial.flight.dto.FlightResponseDto;
import com.example.flights_1p_parcial.flight.infrastructure.FlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;

    public FlightService(FlightRepository flightRepository, ModelMapper modelMapper) {
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
    }

    public FlightResponseDto createFlight(FlightRequestDto dto) {
        if (flightRepository.existsByFlightNumber(dto.getFlightNumber())) {
            throw new ConflictException("Flight number " + dto.getFlightNumber() + " already exists");
        }
        if (!dto.getDepartureTime().isBefore(dto.getArrivalTime())) {
            throw new BadRequestException("Departure time must be before arrival time");
        }
        Flight flight = modelMapper.map(dto, Flight.class);
        Flight savedFlight = flightRepository.save(flight);
        return modelMapper.map(savedFlight, FlightResponseDto.class);
    }

    public List<FlightResponseDto> searchFlights(String flightNumber, String airline,
                                                 LocalDateTime from, LocalDateTime to) {
        String number = (flightNumber == null) ? "" : flightNumber;
        String airlineFilter = (airline == null) ? "" : airline;
        LocalDateTime start = (from == null) ? LocalDateTime.of(1900, 1, 1, 0, 0) : from;
        LocalDateTime end = (to == null) ? LocalDateTime.of(3000, 1, 1, 0, 0) : to;

        return flightRepository
                .findByFlightNumberContainingIgnoreCaseAndAirlineContainingIgnoreCaseAndDepartureTimeBetween(
                        number, airlineFilter, start, end)
                .stream()
                .map(flight -> modelMapper.map(flight, FlightResponseDto.class))
                .toList();
    }
}