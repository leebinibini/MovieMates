package com.nc13.moviemates.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class PaymentModel {
    private Long id;
    private Long reservation_id;
    private Date payment_date;
    private int payment_amount;
    private String payment_method;
    private String payment_status;
}
