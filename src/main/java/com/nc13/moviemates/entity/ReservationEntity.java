package com.nc13.moviemates.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table (name = "reservations")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long scheduleId;

    private LocalDateTime reservationDate;
    private int seatNumber;
    private Long paymentId;
    private int ticketPrice;
}
