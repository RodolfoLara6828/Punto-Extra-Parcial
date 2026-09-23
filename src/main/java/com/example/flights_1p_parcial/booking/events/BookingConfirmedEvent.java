package com.example.flights_1p_parcial.booking.events;

import java.time.LocalDateTime;

public record BookingConfirmedEvent(
        Long bookingId,
        String firstName,
        String lastName,
        String flightNumber,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        LocalDateTime bookingDate
) {
}