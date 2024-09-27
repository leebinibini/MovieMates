package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.QHistoryEntity;
import com.nc13.moviemates.queryDsl.HistoryQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HistoryQueryDSLImpl implements HistoryQueryDSL {

    private final JPAQueryFactory jpaQueryFactory;
    private final QHistoryEntity qHistory = QHistoryEntity.historyEntity;



    @Override
    public List<HistoryEntity> getAll() {
        return jpaQueryFactory.selectFrom(qHistory).fetch();
    }

  /*  @Override
    public Boolean save(HistoryEntity email) {
        return null;
    }*/

    @Override
    public Optional<HistoryEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qHistory).where(qHistory.id.eq(id)).fetchCount() > 0;
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qHistory.id.count()).from(qHistory).fetchOne();
    }
/*
    @Override
    public Boolean deleteById(Long id) {
        return null;
    }*/
}
