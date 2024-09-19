package com.nc13.moviemates.model.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

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
    private Date showDate;
    private Date showTime;
}
