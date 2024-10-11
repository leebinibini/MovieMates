package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.QWishEntity;
import com.nc13.moviemates.entity.WishEntity;
import com.nc13.moviemates.queryDsl.WishQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class WishQueryDSLImpl implements WishQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private final QWishEntity qWish = QWishEntity.wishEntity;

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
                        .and(qWish.movieId.eq(movieId))
                )
                .fetchOne();

        return result != null;
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qWish).where(qWish.id.eq(id)).fetchCount()>0;
    }
}
