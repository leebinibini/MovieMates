package com.nc13.moviemates.model.repository;

import com.nc13.moviemates.model.entity.PaymentEntity;
import com.nc13.moviemates.model.querydsl.PaymentQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long>, PaymentQueryDSL {
}
