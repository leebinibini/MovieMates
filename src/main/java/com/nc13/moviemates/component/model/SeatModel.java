package com.nc13.moviemates.component.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatModel {
    private Long id;
    private Long scheduleId;
    private int row;
    private int column;
}
