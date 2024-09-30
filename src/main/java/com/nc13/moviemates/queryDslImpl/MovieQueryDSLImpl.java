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

    public List<MovieEntity> findChart(){
        QMovieEntity movie = QMovieEntity.movieEntity;
        return jpaQueryFactory
                .selectFrom(movie)  // movie 엔티티 전체 선택
                .orderBy(movie.booking.desc())  // 예매율로 내림차순 정렬
                .limit(5)  // 상위 5개 데이터 가져오기
                .fetch();  // 결과 목록 반환
    }

}
