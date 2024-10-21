package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.QMovieEntity;
import com.nc13.moviemates.entity.QWishEntity;
import com.nc13.moviemates.entity.WishEntity;
import com.nc13.moviemates.queryDsl.WishQueryDSL;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class WishQueryDSLImpl implements WishQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private final QWishEntity qWish = QWishEntity.wishEntity;
    private final QMovieEntity qmovie = QMovieEntity.movieEntity;

    @Override
    public List<WishEntity> getAll() {
        return jpaQueryFactory.selectFrom(qWish).fetch();
    }

    @Override
    public WishEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qWish.id.count()).from(qWish).fetchOne();
    }

    @Override
    public Optional<WishEntity> findByUserIdAndMovieId(Long userId, Long movieId) {
        WishEntity result = jpaQueryFactory.selectFrom(qWish)
                .where(qWish.userId.eq(userId)
                        .and(qWish.movieId.eq(movieId))
                )
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public boolean existsByUserIdAndMovieId(Long userId, Long movieId) {
        WishEntity result = jpaQueryFactory.selectFrom(qWish)
                .where(qWish.userId.eq(userId)
                        .and(qWish.movieId.eq(movieId)))
                .fetchOne();

        return result != null;
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qWish).where(qWish.id.eq(id)).fetchCount() > 0;
    }

    @Override
    public List<Map<String, Object>> findWishesWithMovieDetails(Long userId) {
        List<Tuple> results = jpaQueryFactory
                .select(qWish.id, qWish.userId, qWish.movieId, qmovie.title, qmovie.lengthPosterUrl, qmovie.plot)
                .from(qWish)
                .join(qmovie).on(qWish.movieId.eq(qmovie.id))  // movieId로 조인
                .where(qWish.userId.eq(userId))
                .fetch();  // Tuple로 결과를 가져옴

        // Tuple을 Map<String, Object>로 변환
        List<Map<String, Object>> wishMovie = new ArrayList<>();

        for (Tuple tuple : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("wishId", tuple.get(qWish.id));
            map.put("userId", tuple.get(qWish.userId));
            map.put("movieId", tuple.get(qWish.movieId));
            map.put("movieTitle", tuple.get(qmovie.title));
            map.put("posterUrl", tuple.get(qmovie.lengthPosterUrl));
            map.put("plot", tuple.get(qmovie.plot));

            wishMovie.add(map);  // 변환된 Map을 리스트에 추가
        }

        return wishMovie;  // Map 리스트 반환
    }
}
