package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.model.ReviewModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ReviewEntity;
import com.nc13.moviemates.repository.MovieRepository;
import com.nc13.moviemates.repository.ReservationRepository;
import com.nc13.moviemates.repository.ReviewRepository;
import com.nc13.moviemates.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final MovieRepository movieRepository;
    private final ReservationRepository reservationRepository;

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

    @Override
    public List<ReviewModel> findAllByMovieId(Long movieId) {
        List<ReviewEntity> entList =  repository.findAllByMovieId(movieId);

        // ReviewEntity 리스트를 ReviewModel 리스트로 변환
        List<ReviewModel> modelList = entList.stream()
                .map(entity -> ReviewModel.builder()
                        .id(entity.getId())
                        .content(entity.getContent())
                        .rating(entity.getRating())
                        .movieId(entity.getMovieId())
                        .writerId(entity.getWriterId())
                        .build())
                .collect(Collectors.toList());

        return modelList;
    }

    @Transactional
    @Override
    public Long deleteMany(List<Long> reviewIdList) {
        return repository.deleteMany(reviewIdList);
    }

    @Override
    public List<String> findMovieTitlesByUserId(Long userId) {
        return repository.findMovieTitlesByUserId(userId);
    }

    public boolean hasUserWatchedMovie(Long userId, Long movieId) {
        // 예약 또는 관람 이력 조회
        return reservationRepository.existsByUserIdAndMovieId(userId, movieId);
    }

}
