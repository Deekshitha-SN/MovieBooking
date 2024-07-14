package com.example.movie.helper;

import com.example.movie.interfaces.SeatLockProvider;
import com.example.movie.model.SeatLock;
import com.example.movie.model.Show;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.lang.NonNull;

import java.util.*;
@RequiredArgsConstructor
public class InMemorySeatLockProvider implements SeatLockProvider {

    private final Integer lockTimeout;
    private final Map<Show, Map<Long, SeatLock>> locks =  new HashMap<>();


    @SneakyThrows
    @Override
    synchronized public void lockSeats(@NonNull final Show show, @NonNull final List<Long> seats,
                                       @NonNull final Long userId) {
        for (Long seat : seats) {
            if (isSeatLocked(show, seat)) {
                throw new Exception("Seat locked");
            }
        }

        for (Long seat : seats) {
            lockSeat(show, seat, userId, lockTimeout);
        }
    }

    private void lockSeat(Show show, Long seatNo, Long userId, Integer timeoutInSeconds) {
        if (!locks.containsKey(show)) {
            locks.put(show, new HashMap<>());
        }
        SeatLock lock = new SeatLock(seatNo, show, timeoutInSeconds, new Date(), userId);
        locks.get(show).put(seatNo, lock);
    }

    private boolean isSeatLocked(final Show show, final Long seatNo) {
        return locks.containsKey(show) && locks.get(show).containsKey(seatNo) && !locks.get(show).get(seatNo).isLockExpired();
    }
}
