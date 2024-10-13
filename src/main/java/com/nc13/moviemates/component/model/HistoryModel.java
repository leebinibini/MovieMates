package com.nc13.moviemates.component.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryModel {
    private Long id;
    private String poster;
    private String room;
    private String title;
    private Date date;
    private Date time;
    private String seat;
    private Long reservations;
    private Long userId;
    private Long movieId;
}
