package com.example.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Theatre {
    private Long id;
    private String name;
    private Integer totalCinemaHalls;
    private Long cityId;
}
