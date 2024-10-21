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
    Long deleteMany(List<Long> movieIdList);
    void update(MovieModel movieModel);

    List<MovieEntity> findWatchedMoviesByUserId(Long userId);

    List<MovieEntity> findChart();
    Long findMovieIdByName(String name);
}
