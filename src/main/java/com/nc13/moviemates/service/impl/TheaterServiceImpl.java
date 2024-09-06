package com.nc13.moviemates.service.impl;

import com.nc13.moviemates.model.entity.TheaterEntity;
import com.nc13.moviemates.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheaterServiceImpl implements TheaterService{
    private final TheaterService service;

    @Override
    public List<TheaterEntity> findAll() {
        return service.findAll();
    }

    @Override
    public TheaterEntity save(TheaterEntity email) {
        return service.save(email);
    }

    @Override
    public Optional<TheaterEntity> findById(Long id) {
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
