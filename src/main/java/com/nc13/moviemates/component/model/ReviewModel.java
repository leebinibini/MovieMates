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
public class ReviewModel {
    private Long id;
    private Long movieId;
    private Long writerId;
    private Float rating;
    private String content;
    private Date date;

}
