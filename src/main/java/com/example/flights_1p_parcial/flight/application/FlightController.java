package com.example.flights_1p_parcial.flight.application;

import com.example.flights_1p_parcial.flight.domain.FlightService;
import com.example.flights_1p_parcial.flight.dto.FlightRequestDto;
import com.example.flights_1p_parcial.flight.dto.FlightResponseDto;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/create")
    public ResponseEntity<FlightResponseDto> createFlight(@Valid @RequestBody FlightRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createFlight(dto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightResponseDto>> searchFlights(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airline,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTo) {
        return ResponseEntity.ok(flightService.searchFlights(flightNumber, airline, departureFrom, departureTo));
    }
}