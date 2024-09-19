package com.nc13.moviemates.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table (name = "reservations")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long ScheduleId;
    private Date reservaitonDate;
    private int seatNumber;
    private Long paymentId;
    private int ticketPrice;
}
