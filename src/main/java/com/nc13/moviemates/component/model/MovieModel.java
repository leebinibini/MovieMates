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
    private String genre;
    private String director;
    private String actors;
    private double rate;
    private String plot;
    private String releaseDate;
    private int runningTime;
    private String ageClass;
    private int booking;
    private String widthPosterUrl;
    private String lengthPosterUrl;
    private String posterUrl;
    private int isShowing;

    public MovieModel(String title, Date releaseDate, String runningTime, String information, String genre, String director) {
    }

}
