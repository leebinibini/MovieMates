package com.nc13.moviemates.component.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PosterModel {
    private Long id;
    private Long movieId;
    private String url;
}
