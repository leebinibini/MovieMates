package com.nc13.moviemates.model.querydsl.impl;

import com.nc13.moviemates.model.entity.QScheduleEntity;
import com.nc13.moviemates.model.entity.ScheduleEntity;
import com.nc13.moviemates.model.querydsl.ScheduleQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ScheduleQueryDSLImpl implements ScheduleQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private QScheduleEntity qSchedule = QScheduleEntity.scheduleEntity;

    @Override
    public List<ScheduleEntity> getAll() {
        return List.of();
    }

    @Override
    public ScheduleEntity getById(Long id) {
        return null;
    }

    @Override
    public Boolean exists(Long id) {
        return null;
    }

    @Override
    public Long getRowCount() {
        return 0L;
    }
}
