package com.example.movie.service;

import com.example.movie.dto.BookingDto;
import com.example.movie.enums.BookingStatus;
import com.example.movie.helper.InMemorySeatLockProvider;
import com.example.movie.interfaces.BookingService;
import com.example.movie.interfaces.MovieService;
import com.example.movie.interfaces.SeatLockProvider;
import com.example.movie.model.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    // booking data
    private static final Map<Long, Booking> bookingTable = new HashMap<>();
    private static final Map<Long, ShowSeat> showSeatTable = new HashMap<>();
    private static final Map<Long, Seat> seatTable = new HashMap<>();

    private final SeatLockProvider seatLockProvider = new InMemorySeatLockProvider(60);
    private final MovieService movieService;

    @SneakyThrows
    @Override
    public  Booking createBooking(BookingDto bookingDto) {
        if (isAnySeatAlreadyBooked(bookingDto.getShowId(), bookingDto.getSeatNos())) {
            throw new Exception("seat booked");
        }
        if (isSeatOutOfRange(bookingDto.getShowId(), bookingDto.getSeatNos())) {
            throw new Exception("seat number out of range");
        }

        Show show = movieService.getShow(bookingDto.getShowId());
        seatLockProvider.lockSeats(show, bookingDto.getSeatNos(), bookingDto.getUserId());

        Booking newBooking = new Booking((long) bookingTable.size()+ 1, bookingDto.getUserId(), bookingDto.getShowId(), BookingStatus.CONFIRMED);
        bookingTable.put(newBooking.getId(), newBooking);

        for (Long seatNo: bookingDto.getSeatNos()) {
            Seat newSeat = new Seat((long) seatTable.size() + 1, seatNo, show.getScreenId());
            seatTable.put(newSeat.getId(), newSeat);

            ShowSeat showSeat = new ShowSeat((long) showSeatTable.size() + 1, newSeat.getId(), show.getId(), newBooking.getId());
            showSeatTable.put(showSeat.getId(), showSeat);
        }

        System.out.println("booked successfully for booking Id " + newBooking.getId() + "seat No " + bookingDto.getSeatNos().stream().map(Object::toString).collect(Collectors.joining(","))) ;
        return newBooking;
    }

    private boolean isSeatOutOfRange(Long showId, List<Long> seatNos) {
        Screen screen = movieService.getScreenForShow(showId);
        return seatNos.stream().anyMatch(seatNo -> seatNo <= 0 || seatNo >= screen.getSeatCount());
    }

    private static boolean isAnySeatAlreadyBooked(Long showId, List<Long> seatNos) {
        List<Booking> bookingsForCurrentShow = bookingTable.values().stream()
                .filter(booking -> booking.getShowId().equals(showId))
                .filter(booking -> booking.getBookingStatus().equals(BookingStatus.CONFIRMED))
                .toList();

        List<ShowSeat> showSeats = bookingsForCurrentShow.stream()
                .flatMap(booking -> showSeatTable.values().stream()
                        .filter(showSeat -> showSeat.getBookingId().equals(booking.getId())))
                .toList();

        List<Long> seats = showSeats.stream().map(showSeat -> seatTable.get(showSeat.getSeatId()))
                .map(Seat::getSeatNo).distinct().toList();

        for (Long seatNo : seatNos) {
            if (seats.contains(seatNo)) {
                return true;
            }
        }
        return false;
    }
}
