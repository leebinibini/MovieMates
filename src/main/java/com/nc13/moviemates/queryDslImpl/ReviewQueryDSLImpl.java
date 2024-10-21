package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.QMovieEntity;
import com.nc13.moviemates.entity.QReviewEntity;
import com.nc13.moviemates.entity.ReviewEntity;
import com.nc13.moviemates.queryDsl.ReviewQueryDSL;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReviewQueryDSLImpl implements ReviewQueryDSL {
    @PersistenceContext
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final QReviewEntity qReview = QReviewEntity.reviewEntity;
    private final QMovieEntity qMovie = QMovieEntity.movieEntity;
    @Override
    public List<ReviewEntity> getAll() {
        return jpaQueryFactory.selectFrom(qReview).fetch();
    }

    @Override
    public ReviewEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qReview.id.count()).from(qReview).fetchOne();
    }

    @Override
    public List<MovieEntity> findWatchedMoviesByUserId(Long userId) {
        return jpaQueryFactory
                .select(qMovie)  // 영화 제목을 선택
                .from(qReview)
                .join(qMovie).on(qReview.movieId.eq(qMovie.id))  // 리뷰에 연결된 영화를 조인
                .where(qReview.writerId.eq(userId))  // 리뷰의 userId로 필터링
                .fetch();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qReview).where(qReview.id.eq(id)).fetchCount()>0;
    }

    @Override
    public Long deleteMany(List<Long> reviewIdList) {
        long deletedCount = jpaQueryFactory
                .delete(qReview)
                .where(qReview.id.in(reviewIdList))
                .execute();

        return deletedCount; // 삭제된 행의 수 반환
    }


    @Override
    public List<ReviewEntity> findAllByMovieId(Long movieId) {
        return jpaQueryFactory.selectFrom(qReview).where(qReview.movieId.eq(movieId)).fetch();
    }

    @Override
    public List<String> findMovieTitlesByUserId(Long userId) {
        QReviewEntity review = QReviewEntity.reviewEntity;
        QMovieEntity movie = QMovieEntity.movieEntity;

        return jpaQueryFactory
                .select(movie.title)
                .from(review)
                .join(movie).on(review.movieId.eq(movie.id))  // 리뷰의 movieId와 영화의 id 조인
                .where(review.writerId.eq(userId))  // 리뷰의 writerId가 userId와 일치하는 경우
                .fetch();
    }
    @Override
    public List<ReviewEntity> getReviewsByWriterId(Long writerId){
        return jpaQueryFactory
                .selectFrom(qReview)
                .where(qReview.writerId.eq(writerId))
                .fetch();
    }

    @Override
    public List<Map<String, Object>> findReviewsWithMovieByUserId (Long userId){
        QMovieEntity qMovie = QMovieEntity.movieEntity;
        List<Tuple> reviewWithMovie= jpaQueryFactory.

                    select(
                            qReview.id, qReview.movieId,
                            qReview.writerId, qReview.date,
                            qReview.content, qReview.rating,
                            qMovie.title, qMovie.lengthPosterUrl     // 영화 포스터 URL
                    )
                    .from(qReview)
                    .join(qMovie).on(qReview.movieId.eq(qMovie.id))
                    .where(qReview.writerId.eq(userId))
                    .fetch();
        return reviewWithMovie.stream()
                .map(tuple -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("reviewId", tuple.get(qReview.id));
                    map.put("movieId", tuple.get(qReview.movieId));
                    map.put("writerId", tuple.get(qReview.writerId));
                    map.put("date", tuple.get(qReview.date));
                    map.put("content", tuple.get(qReview.content));
                    map.put("rating", tuple.get(qReview.rating));
                    map.put("title", tuple.get(qMovie.title));
                    map.put("posterUrl", tuple.get(qMovie.lengthPosterUrl)); // 영화 포스터 URL 추가
                    return map;
                })
                .collect(Collectors.toList());
    }
    }




