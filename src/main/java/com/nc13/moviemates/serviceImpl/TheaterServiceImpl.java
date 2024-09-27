package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.entity.TheaterEntity;
import com.nc13.moviemates.repository.TheaterRepository;
import com.nc13.moviemates.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheaterServiceImpl implements TheaterService{
    private final TheaterRepository repository;

    @Override
    public List<TheaterEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Boolean save(TheaterEntity theater) {
         TheaterEntity ent = repository.save(theater);
         Long id =  ent.getId();
        return existsById(id);
    }

    @Override
    public Optional<TheaterEntity> findById(Long id) {
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
    @Transactional
    public Long deleteMany(List<Long> theaterIdList) {
        return repository.deleteMany(theaterIdList);
    }

    @Override
    public Boolean deleteById(Long id) {
         repository.deleteById(id);
        return !existsById(id);
    }

}
