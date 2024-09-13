package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface MovieQueryDSL {
    List<MovieEntity> getAll();

    MovieEntity getById(Long id);

    Boolean exists(Long id);

    Long getRowCount();


}
