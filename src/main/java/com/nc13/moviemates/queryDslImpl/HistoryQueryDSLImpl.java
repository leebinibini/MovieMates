package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.QHistoryEntity;
import com.nc13.moviemates.entity.QMovieEntity;
import com.nc13.moviemates.queryDsl.HistoryQueryDSL;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HistoryQueryDSLImpl implements HistoryQueryDSL {

    private final JPAQueryFactory jpaQueryFactory;
    private final QHistoryEntity qHistory = QHistoryEntity.historyEntity;


    @Override
    public List<MovieEntity> findMovieByUserId(Long userId) {
        QMovieEntity qMovie = QMovieEntity.movieEntity;

        // userId를 기반으로 히스토리 테이블에서 movieId를 가져오고, 이를 이용해 영화 목록 조회
        return jpaQueryFactory
                .select(qMovie)
                .from(qHistory)
                .join(qMovie).on(qHistory.movieId.eq(qMovie.id))  // 히스토리의 movieId와 영화 테이블의 id를 조인
                .where(qHistory.userId.eq(userId))  // 히스토리의 userId가 입력값과 일치하는 경우
                .fetch();  // 결과를 리스트로 반환
    }

    @Override
    public Optional<MovieEntity> findMovieForReview(Long userId, Long movieId) {
        QMovieEntity qMovie = QMovieEntity.movieEntity;
        QHistoryEntity qHistory = QHistoryEntity.historyEntity;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qHistory.userId.eq(userId));

        if (movieId != null) {
            builder.and(qHistory.movieId.eq(movieId));
        } else {
            builder.and(qHistory.movieId.isNull());
        }

        MovieEntity movie = jpaQueryFactory
                .select(qMovie)
                .from(qHistory)
                .join(qMovie).on(qHistory.movieId.eq(qMovie.id))
                .where(builder)
                .fetchFirst();  // 첫 번째 결과만 반환

        return Optional.ofNullable(movie);
    }

    @Override
    public List<HistoryEntity> getAll() {
        return jpaQueryFactory.selectFrom(qHistory).fetch();
    }


    @Override
    public Optional<HistoryEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qHistory).where(qHistory.id.eq(id)).fetchCount() > 0;
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qHistory.id.count()).from(qHistory).fetchOne();
    }

    @Override
    public Optional<HistoryEntity> findByHistoryId(Long id) {
        QHistoryEntity historyEntity = QHistoryEntity.historyEntity;

        HistoryEntity history = jpaQueryFactory
                .selectFrom(historyEntity)
                .where(historyEntity.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(history);
    }

    @Override
    public List<HistoryEntity> findByUserId(Long id) {
        return jpaQueryFactory
                .selectFrom(qHistory)
                .where(qHistory.userId.eq(id))
                .fetch();  // fetch()를 사용하여 여러 결과를 가져옵니다.
    }
    public boolean hasWatchedMovie(Long userId, Long movieId) {
        return jpaQueryFactory
                .selectFrom(qHistory)
                .where(
                        qHistory.userId.eq(userId),
                        qHistory.movieId.eq(movieId)
                )
                .fetchCount() > 0; // 관람 기록이 1개 이상이면 true 반환
    }

}
