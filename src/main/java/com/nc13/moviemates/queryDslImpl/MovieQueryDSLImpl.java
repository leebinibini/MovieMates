package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.QMovieEntity;
import com.nc13.moviemates.queryDsl.MovieQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MovieQueryDSLImpl implements MovieQueryDSL {
    @PersistenceContext
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final QMovieEntity qMovie = QMovieEntity.movieEntity;

    @Override
    public List<MovieEntity> getAll() {
        return jpaQueryFactory.selectFrom(qMovie).fetch();
    }

    @Override
    public Long deleteMany(List<Long> movieIdList) {

        // QueryDSL을 사용하여 여러 영화 삭제
        long deletedCount = jpaQueryFactory
                .delete(qMovie)
                .where(qMovie.id.in(movieIdList))
                .execute();

        return deletedCount; // 삭제된 행의 수 반환
    }

    @Override
    public void update(MovieModel movie) {
        QMovieEntity qMovie = QMovieEntity.movieEntity;

        JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, qMovie);
        updateClause
                .where(qMovie.id.eq(movie.getId()))
                .set(qMovie.title, movie.getTitle())
                .set(qMovie.director, movie.getDirector())
                .execute();
    }

    @Override
    public MovieEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentedMethod'getById'");
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

}
