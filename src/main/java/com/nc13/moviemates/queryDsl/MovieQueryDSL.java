package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.entity.MovieEntity;

import java.util.List;

public interface MovieQueryDSL {
    List<MovieEntity> getAll();
    MovieEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
    List<String> getNowPlayingList();
    List<MovieEntity> findWatchedMoviesByUserId(Long userId);
    Long deleteMany(List<Long> movieIdList);
    void update(MovieModel movieModel);
    List<MovieEntity> findChart();
    Long findMovieIdByName(String name);
}
