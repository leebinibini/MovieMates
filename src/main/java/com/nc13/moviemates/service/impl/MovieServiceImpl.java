package com.nc13.moviemates.service.impl;

import com.nc13.moviemates.model.entity.MovieEntity;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieService service;

    @Override
    public List<MovieEntity> findAll() {
        return service.findAll();
    }

    @Override
    public MovieEntity save(MovieEntity email) {
        return service.save(email);
    }

    @Override
    public Optional<MovieEntity> findById(Long id) {
        return service.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return service.existsById(id);
    }

    @Override
    public Long count() {
        return service.count();
    }

    @Override
    public Integer deleteById(Long id) {
        return service.deleteById(id);
    }
}
