package com.example.flights_1p_parcial.flight.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FlightResponseDto {
    private Long id;
    private String flightNumber;
    private String airline;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer availableSeats;
}