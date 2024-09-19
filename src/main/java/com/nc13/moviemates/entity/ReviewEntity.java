package com.nc13.moviemates.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@Table (name="reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long movieId;
    private Long writerId;
    private Float rating;
    private String content;
    private Date date;
}
