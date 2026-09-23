package com.example.flights_1p_parcial.flight.infrastructure;

import com.example.flights_1p_parcial.flight.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightNumber(String flightNumber);

    List<Flight> findByFlightNumberContainingIgnoreCaseAndAirlineContainingIgnoreCaseAndDepartureTimeBetween(
            String flightNumber, String airline, LocalDateTime from, LocalDateTime to);
}