package com.example.flights_1p_parcial.booking.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

@Component
public class BookingEmailListener {

    private final Logger logger = LoggerFactory.getLogger(BookingEmailListener.class);

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleBookingConfirmed(BookingConfirmedEvent event) {
        DateTimeFormatter iso = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        String content = "Booking confirmation\n"
                + "Booking ID: " + event.bookingId() + "\n"
                + "Passenger: " + event.firstName() + " " + event.lastName() + "\n"
                + "Flight number: " + event.flightNumber() + "\n"
                + "Departure: " + event.departureTime().format(iso) + "\n"
                + "Arrival: " + event.arrivalTime().format(iso) + "\n"
                + "Booking date: " + event.bookingDate().format(iso) + "\n";

        Path file = Path.of("flight_booking_email_" + event.bookingId() + ".txt");
        try {
            Files.writeString(file, content);
            logger.info("Confirmation email written to {}", file.toAbsolutePath());
        } catch (IOException e) {
            logger.error("Could not write confirmation email: {}", e.getMessage());
        }
    }
}