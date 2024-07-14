package com.example.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Screen {
    private Long id;
    private String name;
    private Long theatreId;
    private Long seatCount;
}
