package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.entity.UserEntity;

import java.util.List;

public interface UserQueryDSL {
    List<UserEntity> getAll();
    UserEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
}
