package com.nc13.moviemates.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@Table(name="reviewmovie")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewMovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long reviewId;
    private Long movieId;
    private Long writerId;
    private Date date;
    private String content;
    private Float rating;
    private String title;
    private String lengthPosterUrl;
}
