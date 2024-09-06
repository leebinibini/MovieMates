package com.nc13.moviemates.service;

import com.nc13.moviemates.model.entity.WishEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface WishService {
    List<?> findAll();

    Optional<WishEntity> findById(Long id);

    Boolean save(WishEntity wishList);

    Boolean deleteById(Long id);

    Long count();

    Boolean existsById(Long id);
}
