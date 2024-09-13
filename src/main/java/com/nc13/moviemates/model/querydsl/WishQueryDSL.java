package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.WishEntity;

import java.util.List;

public interface WishQueryDSL {
    List<WishEntity> getAll();

    WishEntity getById(Long id);

    Long getRowCount();

    Boolean exists(Long id);
}
