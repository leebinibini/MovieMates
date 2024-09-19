package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.entity.ReservationEntity;
import com.nc13.moviemates.repository.ReservationRepository;
import com.nc13.moviemates.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository repository;

    @Override
    public List<?> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ReservationEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Boolean save(ReservationEntity reservation) {
        ReservationEntity ent = repository.save(reservation);
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
