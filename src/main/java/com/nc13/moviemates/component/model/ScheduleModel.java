package com.nc13.moviemates.component.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleModel {
    private Long id;
    private Long theaterId;
    private Long movieId;
    private LocalDate showDate;
    private LocalTime showTime;
    private String location;
    private String movie;

}
