package com.nc13.moviemates.service;

import com.nc13.moviemates.entity.MovieEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<MovieEntity> findAll();

    List<String> getNowPlayingList();

    Boolean save(MovieEntity email);

    Optional<MovieEntity> findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);

   // void crawlMovies() throws IOException;

}
