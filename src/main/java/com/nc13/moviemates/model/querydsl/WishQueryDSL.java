package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.WishEntity;

import java.util.List;

public interface WishQueryDSL {
    List<WishEntity> getAll();
    WishEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
}
