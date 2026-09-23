package com.example.flights_1p_parcial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Flights1pParcialApplication {
    public static void main(String[] args) {
        SpringApplication.run(Flights1pParcialApplication.class, args);
    }
}