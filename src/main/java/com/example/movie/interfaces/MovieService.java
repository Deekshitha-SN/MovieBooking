package com.example.movie.interfaces;

import com.example.movie.model.*;

import java.util.List;

public interface MovieService {

    List<Movie> getMoviesAvailableInCity(Long cityId);

    List<Theatre> getTheatresForMovieInCity(Long movieId, Long cityId);

    List<Show> getShowsForTheatre(Long theatreId);

    Show getShow(Long showId);

    Screen getScreenForShow(Long showId);

    List<City> getCities();
}
