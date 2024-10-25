package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.entity.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<MovieEntity> getWatchedMoviesByUserId(Long userId);

    MovieEntity findEntityById(Long id);

    List<MovieEntity> findAll();

    List<MovieEntity> findChart();

    Long save(MovieModel movie);

    Boolean update(List<MovieModel> movieList);

    List<String> getNowPlayingList();

    Optional<MovieModel> findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Long deleteMany(List<Long> movieIdList);

    Boolean deleteById(Long id);

    Long findMovieIdByName(String name);

    Page<MovieModel> findSearchList(String searchStr, Pageable pageable);

//    void crawlMovies() throws IOException;

}
