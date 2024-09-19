package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.entity.WishEntity;
import com.nc13.moviemates.repository.WishRepository;
import com.nc13.moviemates.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {
    private final WishRepository repository;

    @Override
    public List<?> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<WishEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Boolean save(WishEntity wishList) {
        WishEntity ent = repository.save(wishList);
        Long id = ent.getId();
        return existsById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        repository.deleteById(id);
        return !existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
