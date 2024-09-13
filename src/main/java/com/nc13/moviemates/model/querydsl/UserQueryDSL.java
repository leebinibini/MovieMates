package com.nc13.moviemates.model.querydsl;

import com.nc13.moviemates.model.entity.UserEntity;

import java.util.List;

public interface UserQueryDSL {
    List<UserEntity> getAll();

    UserEntity getById(Long id);

    Long getRowCount();

    Boolean exists(Long id);
}
