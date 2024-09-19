package com.nc13.moviemates.queryDsl;
import com.nc13.moviemates.entity.PaymentEntity;

import java.util.List;

public interface PaymentQueryDSL {
    List<PaymentEntity> getAll();
    PaymentEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
}
