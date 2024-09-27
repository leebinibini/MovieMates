package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.QTheaterEntity;
import com.nc13.moviemates.entity.TheaterEntity;
import com.nc13.moviemates.queryDsl.TheaterQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class TheaterQueryDSLImpl implements TheaterQueryDSL {
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
    public Long deleteMany(List<Long> theaterIdList) {
        long deletedCount = jpaQueryFactory
                .delete(qTheater)
                .where(qTheater.id.in(theaterIdList))
                .execute();

        return deletedCount;
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qTheater).where(qTheater.id.eq(id)).fetchCount()>0;
    }
}
