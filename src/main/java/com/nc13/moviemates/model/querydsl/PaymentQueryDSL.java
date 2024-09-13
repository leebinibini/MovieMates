package com.nc13.moviemates.model.querydsl;
import com.nc13.moviemates.model.entity.PaymentEntity;

import java.util.List;


public interface PaymentQueryDSL {
    List<PaymentEntity> getAll();

    PaymentEntity getById(Long id);

    Long getRowCount();

    Boolean exists(Long id);
}
