package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.model.ReviewModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ReviewEntity;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.repository.ReviewRepository;
import com.nc13.moviemates.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;

    @Override
    public List<ReviewEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ReviewEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Boolean save(ReviewEntity review) {
        ReviewEntity ent = repository.save(review);
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


    @Transactional
    @Override
    public Long deleteMany(List<Long> reviewIdList) {
        return repository.deleteMany(reviewIdList);
    }
}
