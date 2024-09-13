package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.TheaterEntity;

import java.util.List;

public interface TheaterQueryDSL {
    List<TheaterEntity> getAll();

    TheaterEntity getById(Long id);

    Long getRowCount();

    Boolean exists(Long id);
}
