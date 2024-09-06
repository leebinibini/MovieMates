package com.nc13.moviemates.model.domain;

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
public class MovieDomain {
    private Long id;
    private String title;
    private String information;
    private Date relearseDate;
    private String genre;
    private int duration;
    private Long reviewId;
    private String director;
}
