package com.ujjwal.Movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movierepo;

    public List<Movie> findAllMovies() {
        System.out.println(movierepo.findAll().toString());
        System.out.println("All Movie Function Called");
        return movierepo.findAll();
    }

    public Optional<Movie> findMovieByImdbId(String imdbId) {
        return movierepo.findMovieByImdbId(imdbId);
    }
}
