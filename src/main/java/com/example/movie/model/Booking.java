package com.example.movie.model;

import com.example.movie.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Booking {
    private Long id;
    private Long userId;
    private Long showId;
    private BookingStatus bookingStatus;
}
