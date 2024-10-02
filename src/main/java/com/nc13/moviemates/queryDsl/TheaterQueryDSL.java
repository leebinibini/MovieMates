package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.entity.TheaterEntity;

import java.util.List;

public interface TheaterQueryDSL {
    List<TheaterEntity> getAll();

    TheaterEntity getById(Long id);

    Boolean exists(Long id);
    Long getRowCount();

    List<TheaterEntity> findByMovieId(Long movieId);
}
