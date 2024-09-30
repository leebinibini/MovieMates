package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.component.model.OrderModel;
import com.nc13.moviemates.entity.QMovieEntity;
import com.nc13.moviemates.entity.QScheduleEntity;
import com.nc13.moviemates.entity.QTheaterEntity;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.queryDsl.ScheduleQueryDSL;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ScheduleQueryDSLImpl implements ScheduleQueryDSL {

    private final JPAQueryFactory jpaQueryFactory;
    private final QScheduleEntity qSchedule = QScheduleEntity.scheduleEntity;
    private final QMovieEntity qMovie = QMovieEntity.movieEntity;
    private final QTheaterEntity qTheater = QTheaterEntity.theaterEntity;
    @Override
    public List<ScheduleEntity> getAll() {
        return jpaQueryFactory.selectFrom(qSchedule).fetch();
    }

    @Override
    public ScheduleEntity getById(Long id) {
        return null;
    }

    @Override
    public List<OrderModel> findByMovieId(Long movieId) {
        List<Tuple> results = jpaQueryFactory.select(qTheater.name, qSchedule.showDate, qSchedule.showTime)
                .from(qSchedule)
                .join(qTheater)
                .on(qSchedule.theaterId.eq(qTheater.id))
                .where(qSchedule.movieId.eq(movieId))
                .fetch();


       return results.stream()
                       .map(tuple -> OrderModel.builder()
                               .theaterName(tuple.get(qTheater.name))
                               .showDate(tuple.get(qSchedule.showDate))
                               .showTime(tuple.get(qSchedule.showTime))
                               .build())
               .collect(Collectors.toList());
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
