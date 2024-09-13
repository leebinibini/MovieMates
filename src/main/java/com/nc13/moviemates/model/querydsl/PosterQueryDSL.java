package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.PosterEntity;

import java.util.List;

public interface PosterQueryDSL {
    List<PosterEntity> getAll();
    PosterEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
}
