package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.entity.PosterEntity;

import java.util.List;

public interface PosterQueryDSL {
    List<PosterEntity> getAll();
    PosterEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
}
