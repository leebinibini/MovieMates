package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.entity.WishEntity;

import java.util.List;

public interface WishQueryDSL {
    List<WishEntity> getAll();
    WishEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
}
