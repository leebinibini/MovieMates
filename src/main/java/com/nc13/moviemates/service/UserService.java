package com.nc13.moviemates.service;

import com.nc13.moviemates.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<?> findAll();

    Optional<UserEntity> findById(Long id);

    Boolean save(UserEntity user);

    Boolean deleteById(Long id);

    Long count();

    Boolean existsById(Long id);
}
