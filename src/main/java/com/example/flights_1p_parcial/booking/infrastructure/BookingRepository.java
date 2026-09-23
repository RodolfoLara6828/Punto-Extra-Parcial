package com.example.flights_1p_parcial.booking.infrastructure;

import com.example.flights_1p_parcial.booking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByCustomerIdAndFlightDepartureTimeBeforeAndFlightArrivalTimeAfter(
            Long customerId, LocalDateTime newArrival, LocalDateTime newDeparture);
}