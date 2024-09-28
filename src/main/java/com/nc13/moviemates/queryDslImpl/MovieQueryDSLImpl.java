package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.QMovieEntity;
import com.nc13.moviemates.entity.QPosterEntity;
import com.nc13.moviemates.queryDsl.MovieQueryDSL;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MovieQueryDSLImpl implements MovieQueryDSL {

    private final JPAQueryFactory jpaQueryFactory;
    private final QMovieEntity qMovie = QMovieEntity.movieEntity;

    @Override
    public List<MovieEntity> getAll() {
        return jpaQueryFactory.selectFrom(qMovie).fetch();
    }

    @Override
    public MovieEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }


    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qMovie.id.count()).from(qMovie).fetchOne();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qMovie).where(qMovie.id.eq(id)).fetchCount()>0;
    }


    public List<String> getNowPlayingList() {
        QMovieEntity movie = QMovieEntity.movieEntity;
        return jpaQueryFactory
                .select(movie.title)
                .from(movie)
                .fetch();
    }

    public List<Tuple> findChart(){
        QMovieEntity movie = QMovieEntity.movieEntity;
        QPosterEntity poster = QPosterEntity.posterEntity; // PosterEntity 추가

        return jpaQueryFactory
                .select(movie, poster.url) // MovieEntity와 Poster URL을 함께 선택
                .from(movie)
                .leftJoin(poster).on(movie.id.eq(poster.movieId)) // MovieEntity의 id와 PosterEntity의 movieId로 조인
                .orderBy(movie.booking.desc()) // 예매율(booking)로 내림차순 정렬
                .limit(5) // 상위 5개만 가져오기
                .fetch();
    }

}
