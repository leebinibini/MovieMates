package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.QHistoryEntity;
import com.nc13.moviemates.entity.QMovieEntity;
import com.nc13.moviemates.queryDsl.HistoryQueryDSL;
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

        MovieEntity movie = jpaQueryFactory
                .select(qMovie)
                .from(qHistory)
                .join(qMovie).on(qHistory.movieId.eq(qMovie.id))  // 히스토리의 movieId와 영화 테이블의 id를 조인
                .where(qHistory.userId.eq(userId)  // 히스토리에서 유저 아이디가 일치하고
                        .and(qHistory.movieId.eq(movieId)))  // 히스토리에서 movieId가 일치하는 경우
                .fetchOne();  // 단일 결과 반환

        return Optional.ofNullable(movie);
    }

    @Override
    public List<HistoryEntity> getAll() {
        return jpaQueryFactory.selectFrom(qHistory).fetch();
    }

  /*  @Override
    public Boolean save(HistoryEntity email) {
        return null;
    }*/

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
/*
    @Override
    public Boolean deleteById(Long id) {
        return null;
    }*/
}
