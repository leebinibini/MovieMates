package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.UserModel;
import com.nc13.moviemates.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UserService {
    List<?> findAll();

    Optional<UserEntity> findById(Long id);

    Boolean existsByPassword(String password);

    Boolean save(UserEntity user);

    Boolean deleteById(Long id);

    Long count();

    Boolean existsById(Long id);

    Boolean insert(UserEntity user);

    boolean authenticate(String email, String password);

    Boolean update(UserModel userData);
}
