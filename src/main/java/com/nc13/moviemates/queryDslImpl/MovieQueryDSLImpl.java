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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
                .set(qMovie.posterUrl, movie.getPosterUrl())
                .set(qMovie.genre, movie.getGenre())
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

    @Override
    public List<MovieEntity> findWatchedMoviesByUserId(Long userId) {
        return List.of();
    }

    public List<MovieEntity> findChart(){
        QMovieEntity movie = QMovieEntity.movieEntity;
        return jpaQueryFactory
                .selectFrom(movie)  // movie 엔티티 전체 선택
                .orderBy(movie.booking.desc())  // 예매율로 내림차순 정렬
                .limit(5)  // 상위 5개 데이터 가져오기
                .fetch();  // 결과 목록 반환
    }

    @Override
    public Long findMovieIdByName(String name) {
        return jpaQueryFactory
                .select(qMovie.id)
                .from(qMovie)
                .where(qMovie.title.eq(name))
                .fetchOne();
    }

    @Override
    public Page<MovieEntity> findByTitleContaining(String searchStr, Pageable pageable) {
        List<MovieEntity> movieEntities = jpaQueryFactory
                .selectFrom(qMovie)
                .where(qMovie.director.contains(searchStr).or(qMovie.title.contains(searchStr)))
                .orderBy(qMovie.id.desc())
                .offset(pageable.getOffset())  // 페이징의 시작점
                .limit(pageable.getPageSize()) // 한 페이지에 보여줄 항목 수
                .fetch();

        // 전체 개수 조회 (페이징에 필요한 총 항목 수)
        long total = jpaQueryFactory
                .selectFrom(qMovie)
                .where(qMovie.director.contains(searchStr).or(qMovie.title.contains(searchStr)))
                .fetchCount();

        // Page 객체로 변환하여 반환
        return new PageImpl<>(movieEntities, pageable, total);
    }


}
