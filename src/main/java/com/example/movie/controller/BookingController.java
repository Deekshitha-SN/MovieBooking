package com.example.movie.controller;

import com.example.movie.dto.BookingDto;
import com.example.movie.interfaces.BookingService;
import com.example.movie.model.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/booking")
    public Booking createBooking(@RequestBody BookingDto booking) {
        return bookingService.createBooking(booking);
    }

}
