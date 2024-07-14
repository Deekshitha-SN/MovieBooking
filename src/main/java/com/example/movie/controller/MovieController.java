package com.example.movie.controller;

import com.example.movie.interfaces.MovieService;
import com.example.movie.model.City;
import com.example.movie.model.Movie;
import com.example.movie.model.Show;
import com.example.movie.model.Theatre;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/cities")
    public List<City> getCities(){
        return movieService.getCities();
    }

    @GetMapping("/movies/{cityId}")
    public List<Movie> getMoviesAvailableInCity(@PathVariable Long cityId) {
        return movieService.getMoviesAvailableInCity(cityId);
    }

    @GetMapping("/theatres")
    public List<Theatre> getTheatresForMovieInCity(
            @RequestParam(required = true) Long movieId,
            @RequestParam(required = true) Long cityId) {
        return movieService.getTheatresForMovieInCity(movieId, cityId);
    }

    @GetMapping("/show/{theatreId}")
    public List<Show> getShowsForTheatre(@PathVariable Long theatreId) {
        return movieService.getShowsForTheatre(theatreId);
    }

}
