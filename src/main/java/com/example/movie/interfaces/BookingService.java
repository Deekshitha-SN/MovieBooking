package com.example.movie.interfaces;

import com.example.movie.dto.BookingDto;
import com.example.movie.model.Booking;

public interface BookingService {
   Booking createBooking(BookingDto bookingDto);
}
