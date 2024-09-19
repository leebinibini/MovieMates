package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.TheaterEntity;

import java.util.List;

public interface TheaterQueryDSL {
    List<TheaterEntity> getAll();

    TheaterEntity getById(Long id);

    Boolean exists(Long id);
    Long getRowCount();
}
