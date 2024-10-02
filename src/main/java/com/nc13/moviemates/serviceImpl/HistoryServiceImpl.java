package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.repository.HistoryRepository;
import com.nc13.moviemates.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository repository;

    @Override
    public List<HistoryEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Boolean save(HistoryEntity history) {
        HistoryEntity ent = repository.save(history);
        Long id = ent.getId();
        return existsById(id);
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
        return null;
    }
}
