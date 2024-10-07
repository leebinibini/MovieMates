package com.nc13.moviemates.component.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderModel {
    private String theaterName;
    private LocalDate showDate;
    private LocalTime showTime;

}
