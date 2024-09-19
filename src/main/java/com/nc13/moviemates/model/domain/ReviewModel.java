package com.nc13.moviemates.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class ReviewModel {
    private Long id;
    private Long movieId;
    private Long writerId;
    private Float rating;
    private String content;
    private Date date;
}
