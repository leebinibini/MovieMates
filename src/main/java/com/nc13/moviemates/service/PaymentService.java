package com.nc13.moviemates.service;

import com.nc13.moviemates.entity.PaymentEntity;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface PaymentService {
    List<?> findAll();

    Optional<PaymentEntity> findById(Long id);

    Boolean save(PaymentEntity payment);

    Boolean deleteById(Long id);

    Long count();

    Boolean existsById(Long id);

    Boolean processPaymentAndSave(Payment paymentResponse, Map<String, Object> requestData);
}
