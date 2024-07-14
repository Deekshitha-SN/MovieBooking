package com.example.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ShowSeat {
    private Long id;
    private Long seatId;
    private Long showId;
    private Long bookingId;
}
