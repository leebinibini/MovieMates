package com.nc13.moviemates.service;

import com.nc13.moviemates.entity.WishEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface WishService {
    List<?> findAll();

    Optional<WishEntity> findById(Long id);

    Boolean save(WishEntity wishList);

    Boolean deleteById(Long id);

    Long count();

    Boolean existsById(Long id);
}
