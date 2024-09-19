package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.PaymentEntity;
import com.nc13.moviemates.queryDsl.PaymentQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>, PaymentQueryDSL {
}
