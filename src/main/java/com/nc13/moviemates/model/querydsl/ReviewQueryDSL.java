package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.ReviewEntity;

import java.util.List;

public interface ReviewQueryDSL {
    List<ReviewEntity> getAll();

    ReviewEntity getById(Long id);

    Long getRowCount();

    Boolean exists(Long id);
}
