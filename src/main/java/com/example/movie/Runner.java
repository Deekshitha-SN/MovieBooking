package com.example.movie;

import com.example.movie.dto.BookingDto;
import com.example.movie.interfaces.BookingService;
import com.example.movie.interfaces.MovieService;
import com.example.movie.service.BookingServiceImpl;
import com.example.movie.service.MovieServiceImpl;

import java.util.Arrays;

public class Runner {
    public static void main(String[] args) {
        MovieService movieService = new MovieServiceImpl();
        BookingService bookingService = new BookingServiceImpl(movieService);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setUserId(1L);
        bookingDto.setShowId(1L);
        bookingDto.setSeatNos(Arrays.asList(1L, 2L));

        BookingDto bookingDto2 = new BookingDto();
        bookingDto2.setUserId(1L);
        bookingDto2.setShowId(2L);
        bookingDto2.setSeatNos(Arrays.asList(1L, 2L));

        bookingService.createBooking(bookingDto);
        bookingService.createBooking(bookingDto2);
    }
}
