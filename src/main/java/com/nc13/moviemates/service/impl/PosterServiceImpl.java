package com.nc13.moviemates.service.impl;

import com.nc13.moviemates.model.entity.PosterEntity;
import com.nc13.moviemates.model.entity.ScheduleEntity;
import com.nc13.moviemates.service.PosterService;
import com.nc13.moviemates.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PosterServiceImpl implements PosterService {
    private final PosterService service;

    @Override
    public List<PosterEntity> findAll() {
        return service.findAll();
    }

    @Override
    public PosterEntity save(PosterEntity email) {
        return service.save(email);
    }

    @Override
    public Optional<PosterEntity> findById(Long id) {
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
