package com.nc13.moviemates.service.impl;

import com.nc13.moviemates.model.entity.ScheduleEntity;
import com.nc13.moviemates.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleService service;

    @Override
    public List<ScheduleEntity> findAll() {
        return service.findAll();
    }

    @Override
    public ScheduleEntity save(ScheduleEntity email) {
        return service.save(email);
    }

    @Override
    public Optional<ScheduleEntity> findById(Long id) {
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
