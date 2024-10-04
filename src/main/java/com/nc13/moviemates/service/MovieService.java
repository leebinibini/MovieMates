package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.entity.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    MovieEntity findEntityById(Long id);

    List<MovieEntity> findAll();

    List<MovieEntity> findChart();

    Long save(MovieModel movie);

    List<String> getNowPlayingList();


    Optional<MovieEntity> findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

   Long findMovieIdByName(String name);

   // void crawlMovies() throws IOException;

}
