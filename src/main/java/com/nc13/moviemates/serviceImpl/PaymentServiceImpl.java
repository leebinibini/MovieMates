package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.model.entity.PaymentEntity;
import com.nc13.moviemates.repository.PaymentRepository;
import com.nc13.moviemates.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;

    @Override
    public List<?> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<PaymentEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Boolean save(PaymentEntity payment) {
        PaymentEntity ent = repository.save(payment);
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



