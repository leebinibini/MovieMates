package com.nc13.moviemates.component.model;

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
public class MovieModel {
    private Long id;
    private String title;
    private String information;
    private Date releaseDate;
    private String genre;
    private int runningTime;
    private Long reviewId;
    private String director;
    private int booking;

    public MovieModel(String title, Date releaseDate, String runningTime, String information, String genre, String director) {
    }
}
