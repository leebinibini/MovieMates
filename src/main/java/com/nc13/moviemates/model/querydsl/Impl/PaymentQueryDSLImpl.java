package com.nc13.moviemates.model.querydsl.Impl;

import com.nc13.moviemates.model.entity.PaymentEntity;
import com.nc13.moviemates.model.entity.QPaymentEntity;
import com.nc13.moviemates.model.querydsl.PaymentQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class PaymentQueryDSLImpl implements PaymentQueryDSL{
    private final JPAQueryFactory jpaQueryFactory;
    private final QPaymentEntity qPayment = QPaymentEntity.paymentEntity;

    @Override
    public List<PaymentEntity> getAll() {
        return jpaQueryFactory.selectFrom(qPayment).fetch();
    }

    @Override
    public PaymentEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qPayment.id.count()).from(qPayment).fetchOne();
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qPayment).where(qPayment.id.eq(id)).fetchCount()>0;
    }
}
