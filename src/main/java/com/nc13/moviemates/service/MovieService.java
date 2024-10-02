package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.entity.MovieEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    MovieEntity findEntityById(Long id);

    List<MovieEntity> findAll();

    List<MovieEntity> findChart();

    Long save(MovieModel movie);

    Boolean update(List<MovieModel> movieList);

    List<String> getNowPlayingList();

    Optional<MovieEntity> findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Long deleteMany(List<Long> movieIdList);

    Boolean deleteById(Long id);

   // void crawlMovies() throws IOException;

}
