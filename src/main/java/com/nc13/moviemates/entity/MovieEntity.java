package com.nc13.moviemates.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name= "movies")
public class MovieEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private String director;
    private String filepath;
    private String actors;
    private double rate;
    private String plot;
    private String releaseDate;
    private int runningTime;
    private String WidthPosterUrl;
    private String ageClass;
    private int booking;
    private String lengthPosterUrl;
}
