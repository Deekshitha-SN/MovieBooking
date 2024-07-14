package com.example.movie.interfaces;

import com.example.movie.model.Show;

import java.util.List;

public interface SeatLockProvider {
    void lockSeats(Show show, List<Long> seat, Long userId);
}
