package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.entity.UserEntity;

import java.util.List;

public interface UserQueryDSL {
    UserEntity findByEmail(String email);
    List<UserEntity> getAll();
    UserEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
}
