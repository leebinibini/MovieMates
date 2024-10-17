package com.nc13.moviemates.component.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class PaymentModel {
    private Long id;
    private Long userId;
    private Long seatId;
    private String movie;
    private Date paymentDate;
    private String paymentMethod;
    private String paymentStatus;
    private String impUid;          // Iamport 결제 고유 ID
    private String merchantUid;     // 프론트에서 보낸 주문 번호
    private String buyerName;       // 구매자 이름
    private int amount;
    private String location;
}
