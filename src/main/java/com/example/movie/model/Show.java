package com.example.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
public class Show {
    private Long id;
    private Long screenId;
    private Long movieId;
    private LocalTime startTime;
    private Duration duration;
}
