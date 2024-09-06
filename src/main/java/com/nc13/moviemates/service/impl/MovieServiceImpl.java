package com.nc13.moviemates.service.impl;

import com.nc13.moviemates.model.entity.MovieEntity;
import com.nc13.moviemates.model.repository.MovieRepository;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    @Override
    public List<MovieEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Boolean save(MovieEntity movie) {
       MovieEntity ent = repository.save(movie);
       Long id = ent.getId();
       return existsById(id);
    }

    @Override
    public Optional<MovieEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean deleteById(Long id) {
       repository.deleteById(id);
        return !existsById(id);
    }
}
