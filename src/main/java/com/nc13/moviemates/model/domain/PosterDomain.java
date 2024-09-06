package com.nc13.moviemates.model.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PosterDomain {
    private Long id;
    private Long movieId;
    private String filepath;
    private String filename;
}
