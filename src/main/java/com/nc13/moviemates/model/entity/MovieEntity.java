package com.nc13.moviemates.model.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String information;
    private Date relearseDate;
    private String genre;
    private int duration;
    private Long reviewId;
    private String director;
}
