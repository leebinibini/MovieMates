package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.QScheduleEntity;
import com.nc13.moviemates.entity.QSeatEntity;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.entity.SeatEntity;
import com.nc13.moviemates.queryDsl.SeatQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SeatQueryDSLImpl implements SeatQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private final QScheduleEntity qSchedule = QScheduleEntity.scheduleEntity;
    private final QSeatEntity qSeat = QSeatEntity.seatEntity;

    @Override
    public List<SeatEntity> getAll() {
        return jpaQueryFactory.selectFrom(qSeat).fetch();
    }

    @Override
    public Optional<SeatEntity> findById(Long id) {
        return Optional.empty();
    }


    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qSeat).where(qSeat.id.eq(id)).fetchCount() > 0;
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qSeat.id.count()).fetchOne();
    }

    @Override
    public List<SeatEntity> findSeatsByScheduleId(Long scheduleId) {
        return jpaQueryFactory.select(qSeat)
                .where(qSeat.scheduleId.eq(scheduleId))
                .fetch();
    }


}



