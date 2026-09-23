package com.example.flights_1p_parcial.booking.application;

import com.example.flights_1p_parcial.booking.domain.BookingService;
import com.example.flights_1p_parcial.booking.dto.BookingRequestDto;
import com.example.flights_1p_parcial.booking.dto.BookingResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/flights/book")
    public ResponseEntity<BookingResponseDto> bookFlight(@Valid @RequestBody BookingRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.bookFlight(dto));
    }

    @GetMapping({"/flight/book/{id}", "/flights/book/{id}"})
    public ResponseEntity<BookingResponseDto> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }
}