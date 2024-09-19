package com.nc13.moviemates.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@Table (name="payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Long reservation_id;
    private Date payment_date;
    private int payment_amount;
    private String payment_method;
    private String payment_status;
}
