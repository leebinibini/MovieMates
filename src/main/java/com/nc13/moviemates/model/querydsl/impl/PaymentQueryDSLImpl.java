package com.nc13.moviemates.model.querydsl.impl;

import com.nc13.moviemates.model.entity.PaymentEntity;
import com.nc13.moviemates.model.entity.QPaymentEntity;
import com.nc13.moviemates.model.querydsl.PaymentQueryDSL;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PaymentQueryDSLImpl implements PaymentQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private QPaymentEntity qPayment = QPaymentEntity.paymentEntity;


    @Override
    public List<PaymentEntity> getAll() {
        return List.of();
    }

    @Override
    public PaymentEntity getById(Long id) {
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
