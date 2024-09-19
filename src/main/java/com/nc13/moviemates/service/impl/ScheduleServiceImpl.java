package com.nc13.moviemates.service.impl;

import com.nc13.moviemates.model.entity.ScheduleEntity;
import com.nc13.moviemates.model.repository.ScheduleRepository;
import com.nc13.moviemates.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;

    @Override
    public List<ScheduleEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Boolean save(ScheduleEntity schedule) {
        ScheduleEntity ent = repository.save(schedule);
        Long id = ent.getId();
        return existsById(id);
    }

    @Override
    public Optional<ScheduleEntity> findById(Long id) {
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
