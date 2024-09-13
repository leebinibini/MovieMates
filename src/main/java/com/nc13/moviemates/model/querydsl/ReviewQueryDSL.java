package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.ReviewEntity;

import java.util.List;

public interface ReviewQueryDSL {
    List<ReviewEntity> getAll();
    ReviewEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
}
