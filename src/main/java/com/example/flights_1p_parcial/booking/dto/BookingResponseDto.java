package com.example.flights_1p_parcial.booking.dto;

import java.time.LocalDateTime;

public record BookingResponseDto(
        Long id,
        Long flightId,
        String flightNumber,
        Long customerId,
        String customerFirstName,
        String customerLastName,
        LocalDateTime bookingDate
) {
}