package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.model.WishModel;
import com.nc13.moviemates.entity.WishEntity;
import com.nc13.moviemates.repository.WishRepository;
import com.nc13.moviemates.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    public Optional<WishEntity> findByMovieIdandUserId(Long movieId, Long userId) {
        WishEntity ent = WishEntity.builder()
                .movieId(movieId)
                .userId(userId)
                .build();

        return findById(ent.getId());
    }

    @Override
    public Boolean save(WishModel wish) {
        System.out.println("위시서비스임플" + wish);
        WishEntity ent = WishEntity.builder()
                .userId(wish.getUserId())
                .movieId(wish.getMovieId())
                .build();

        WishEntity savedEntity = repository.save(ent);
        Long id = savedEntity.getId();
        return existsById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        repository.deleteById(id);
        return !existsById(id);
    }

    @Override
    public Boolean delete(WishModel wish) {
        // 1. 유저 ID와 영화 ID로 해당 엔티티를 찾는다.
        Optional<WishEntity> ent = repository.findByUserIdAndMovieId(wish.getUserId(), wish.getMovieId());

        // 2. 엔티티가 존재할 경우 삭제하고, 삭제 성공 여부를 반환한다.
        if (ent.isPresent()) {
            repository.delete(ent.get());  // 엔티티 자체를 삭제
            return true;  // 삭제 성공 시 true 반환
        } else {
            return false;  // 해당 엔티티가 없으면 false 반환
        }
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
    public Boolean existsByMovieIdandUserId(Long movieId, Long userId) {
        Boolean exist = repository.existsByUserIdAndMovieId(userId, movieId);

        return exist;
    }

    @Override
    public Boolean isWishlisted(Long movieId, Long userId) {
        Optional<WishEntity> wishEntity = repository.findByUserIdAndMovieId(userId, movieId);

        return wishEntity != null;  // 존재하면 true, 없으면 false 반환
    }

    @Override
    public List<Map<String, Object>> findWishesWithMovieDetails(Long userId) {
        return repository.findWishesWithMovieDetails(userId);
    }
}
