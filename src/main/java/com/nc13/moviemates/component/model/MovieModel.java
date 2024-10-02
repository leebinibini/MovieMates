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
    private String plot;
    private Date releaseDate;
    private String genre;
    private int runningTime;
    private Long reviewId;
    private String director;
    private String filepath;
    private String posterUrl;
    private String ageClass;
    private double  rate;
    
}
