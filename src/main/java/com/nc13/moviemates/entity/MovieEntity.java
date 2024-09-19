package com.nc13.moviemates.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "movies")
public class MovieEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String information;
    private Date releaseDate;
    private String genre;
    private int runningTime;
    private Long reviewId;
    private String director;
}
