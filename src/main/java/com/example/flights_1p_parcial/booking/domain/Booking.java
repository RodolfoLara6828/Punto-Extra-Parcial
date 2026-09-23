package com.example.flights_1p_parcial.booking.domain;

import com.example.flights_1p_parcial.flight.domain.Flight;
import com.example.flights_1p_parcial.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User customer;

    @ManyToOne
    private Flight flight;

    private String customerFirstName;
    private String customerLastName;
    private LocalDateTime bookingDate;
}