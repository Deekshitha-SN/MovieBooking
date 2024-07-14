package com.example.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Seat {
    private Long id;
    private Long seatNo;
    private Long screenId;
}
