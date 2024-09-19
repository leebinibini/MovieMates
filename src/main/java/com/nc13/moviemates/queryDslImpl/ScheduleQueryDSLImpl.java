package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.QMovieEntity;
import com.nc13.moviemates.entity.QScheduleEntity;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.queryDsl.ScheduleQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class ScheduleQueryDSLImpl implements ScheduleQueryDSL {

    private final JPAQueryFactory jpaQueryFactory;
    private final QScheduleEntity qSchedule = QScheduleEntity.scheduleEntity;
    @Override
    public List<ScheduleEntity> getAll() {
        return jpaQueryFactory.selectFrom(qSchedule).fetch();
    }

    @Override
    public ScheduleEntity getById(Long id) {
       throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qSchedule.id.count()).from(qSchedule).fetchOne();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.select(qSchedule.id.count()).from(qSchedule).fetchCount()>0;
    }
}
