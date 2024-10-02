package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.component.model.TheaterModel;
import com.nc13.moviemates.entity.QTheaterEntity;
import com.nc13.moviemates.entity.TheaterEntity;
import com.nc13.moviemates.queryDsl.TheaterQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class TheaterQueryDSLImpl implements TheaterQueryDSL {
    @PersistenceContext
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private final QTheaterEntity qTheater = QTheaterEntity.theaterEntity;

    @Override
    public List<TheaterEntity> getAll() {
        return jpaQueryFactory.selectFrom(qTheater).fetch();
    }

    @Override
    public TheaterEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qTheater.id.count()).fetchOne();
    }

    @Override
    public List<TheaterEntity> findByMovieId(Long movieId) {
        return jpaQueryFactory.selectFrom(qTheater)
                .where(qTheater.movieId.eq(movieId))
                .fetch();
    }

    @Override
    public Long deleteMany(List<Long> theaterIdList) {
        long deletedCount = jpaQueryFactory
                .delete(qTheater)
                .where(qTheater.id.in(theaterIdList))
                .execute();

        return deletedCount;
    }

    @Override
    public void update(TheaterModel theater) {

        if (theater.getId() == null) {
            TheaterEntity ent = TheaterEntity.builder()
                    .name(theater.getName())
                    .room(theater.getRoom())
                    .location(theater.getLocation())
                    .capacity(theater.getCapacity())
                    .build();
            entityManager.persist(ent);
        } else {
            // ID가 존재하는 경우 업데이트 수행
            JPAUpdateClause updateClause = new JPAUpdateClause(entityManager, qTheater);
            updateClause
                    .where(qTheater.id.eq(theater.getId()))
                    .set(qTheater.name, theater.getName())
                    .set(qTheater.room, theater.getRoom())
                    .set(qTheater.location, theater.getLocation())
                    .set(qTheater.capacity, theater.getCapacity())
                    .execute();
        }
    }


    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qTheater).where(qTheater.id.eq(id)).fetchCount()>0;
    }
}
