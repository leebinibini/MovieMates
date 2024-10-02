package com.nc13.moviemates.service;

import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface HistoryService {

    List<HistoryEntity> findAll();



    Boolean save(HistoryEntity email);

    Boolean existsById(Long id);

    Long count();

    Boolean deleteById(Long id);
}
