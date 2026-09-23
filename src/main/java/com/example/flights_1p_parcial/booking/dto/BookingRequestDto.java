package com.example.flights_1p_parcial.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequestDto {
    @NotNull
    private Long flightId;
}