package com.example.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long userId;
    private Long showId;
    private List<Long> seatNos;
}
