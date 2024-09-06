package com.nc13.moviemates.service;

import com.nc13.moviemates.model.domain.UserDomain;
import com.nc13.moviemates.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {
    List<UserEntity> findAll();

    UserEntity save(UserEntity email);

    Optional<UserEntity> findById(Long id);

    boolean existsById(Long id);

    Long count();

    Integer deleteById(Long id);



}
