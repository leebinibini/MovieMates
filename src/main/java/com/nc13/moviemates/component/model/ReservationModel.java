package com.nc13.moviemates.component.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Data
public class ReservationModel {
    private Long id;
    private Long userId;
    private Long scheduleId;
    private LocalDateTime reservationDate;
    private Long seatId;
    private Long paymentId;
    private int ticketPrice;
    private String status;
    private Long movieId;
}
