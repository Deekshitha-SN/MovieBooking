package com.example.movie.service;

import com.example.movie.interfaces.MovieService;
import com.example.movie.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {
    // admin data
    private static final Map<Long, City> cityTable = new HashMap<>();
    private static final Map<Long, Movie> movieTable = new HashMap<>();
    private static final Map<Long, Theatre> theatreTable = new HashMap<>();
    private static final Map<Long, Screen> screenTable = new HashMap<>();
    private static final Map<Long, Show> showTable = new HashMap<>();

    // seeding data
    static {
        // cities
        City mumbai = addCity("Mumbai");
        City bengaluru = addCity("Bengaluru");
        City chennai = addCity("Chennai");

        // movies
        Movie kalki = addMovie("Kalki", LocalDate.of(2024, 6, 1));
        Movie animal = addMovie("Animal", LocalDate.of(2023, 6, 1));
        Movie robo = addMovie("Robo", LocalDate.of(2013, 6, 10));

        // theatres
        Theatre gopalan = addTheatre(mumbai.getId(), "Gopalan Cinemas", 3);
        Theatre pvr = addTheatre(bengaluru.getId(), "PVR Cineplex", 10);
        Theatre forum = addTheatre(chennai.getId(), "Forum Cinemas", 10);

        // screens
        Screen screen1 = addScreen("ScreenA", gopalan.getId(), 30L);
        Screen screen2 = addScreen("ScreenB", pvr.getId(), 60L);
        Screen screen3 = addScreen("ScreenC", forum.getId(), 100L);

        // show table
        addShow(screen1.getId(), kalki.getId(), LocalTime.of(8,0), Duration.ofMinutes(150));
        addShow(screen1.getId(), kalki.getId(), LocalTime.of(11, 0), Duration.ofMinutes(150));
        addShow(screen1.getId(), kalki.getId(), LocalTime.of(14,0), Duration.ofMinutes(150));
        addShow(screen1.getId(), kalki.getId(), LocalTime.of(17,0), Duration.ofMinutes(150));
        addShow(screen1.getId(), kalki.getId(), LocalTime.of(20,0), Duration.ofMinutes(150));

        addShow(screen2.getId(), animal.getId(), LocalTime.of(8,0), Duration.ofMinutes(150));
        addShow(screen2.getId(), animal.getId(), LocalTime.of(11,0), Duration.ofMinutes(150));
        addShow(screen2.getId(), animal.getId(), LocalTime.of(14,0), Duration.ofMinutes(150));
        addShow(screen2.getId(), animal.getId(), LocalTime.of(17,0), Duration.ofMinutes(150));
        addShow(screen2.getId(), animal.getId(), LocalTime.of(20,0), Duration.ofMinutes(150));

        addShow(screen3.getId(), robo.getId(), LocalTime.of(8,0), Duration.ofMinutes(120));
        addShow(screen3.getId(), robo.getId(), LocalTime.of(11,0), Duration.ofMinutes(120));
        addShow(screen3.getId(), robo.getId(), LocalTime.of(14,0), Duration.ofMinutes(120));
        addShow(screen3.getId(), robo.getId(), LocalTime.of(17,0), Duration.ofMinutes(120));
        addShow(screen3.getId(), robo.getId(), LocalTime.of(20,0), Duration.ofMinutes(120));
    }

    private static City addCity(String name) {
        City newCity = new City((long) cityTable.size() + 1, name);
        cityTable.put(newCity.getId(), newCity);
        return newCity;
    }

    private static Movie addMovie(String name, LocalDate releaseDate) {
        Movie newMovie = new Movie((long) movieTable.size()+1, name, releaseDate);
        movieTable.put(newMovie.getId(), newMovie);
        return newMovie;
    }

    private static Theatre addTheatre(Long cityId, String name, int noOfHalls) {
        Theatre newTheatre = new Theatre((long) theatreTable.size()+1, name, noOfHalls, cityId);
        theatreTable.put(newTheatre.getId(), newTheatre);
        return newTheatre;
    }

    private static Screen addScreen(String name, Long theatreId, Long seatCount) {
        Screen newScreen = new Screen((long) screenTable.size()+1, name, theatreId, seatCount);
        screenTable.put(newScreen.getId(), newScreen);
        return newScreen;
    }

    private static Show addShow(Long screenId, Long movieId, LocalTime startTime, Duration runTime) {
        Show newShow = new Show((long) showTable.size()+1, screenId, movieId, startTime, runTime);
        showTable.put(newShow.getId(), newShow);
        return newShow;
    }

    @Override
    public Show getShow(Long showId) {
        return showTable.get(showId);
    }

    @Override
    public Screen getScreenForShow(Long showId) {
        Show show = getShow(showId);
        return screenTable.get(show.getScreenId());
    }

    @Override
    public List<Theatre> getTheatresForMovieInCity(Long movieId, Long cityId) {
        return showTable.values().stream()
                .filter(show -> show.getMovieId().equals(movieId))
                .map(Show::getScreenId)
                .distinct()
                .map(screenTable::get)
                .map(Screen::getTheatreId)
                .distinct()
                .map(theatreTable::get)
                .filter(theatre -> theatre.getCityId().equals(cityId))
                .toList();
    }

    @Override
    public List<Movie> getMoviesAvailableInCity(Long cityId) {
        List<Long> theatresInCityIds = theatreTable.values().stream()
                .filter(theatre -> theatre.getCityId().equals(cityId))
                .map(Theatre::getId)
                .distinct()
                .toList();

        List<Long> screenIdsFromAllTheatres = screenTable.values().stream()
                .filter(screen -> theatresInCityIds.contains(screen.getTheatreId()))
                .map(Screen::getId)
                .distinct()
                .toList();

        return showTable.values().stream()
                .filter(show -> screenIdsFromAllTheatres.contains(show.getScreenId()))
                .map(Show::getMovieId)
                .distinct()
                .map(movieTable::get)
                .toList();
    }

    @Override
    public List<Show> getShowsForTheatre(Long theatreId) {
        return screenTable.values().stream()
                .filter(screen -> screen.getTheatreId().equals(theatreId))
                .map(Screen::getId)
                .distinct()
                .flatMap(screenId -> showTable.values().stream().filter(show -> show.getScreenId().equals(screenId)))
                .distinct()
                .toList();
    }

    @Override
    public List<City> getCities() {
        return cityTable.values().stream().toList();
    }
}
