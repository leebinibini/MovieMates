package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.PosterModel;
import com.nc13.moviemates.entity.PosterEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PosterService {
    List<PosterEntity> findAll();

    String save(MultipartFile file);

    Optional<PosterEntity> findById(Long id);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);
}
