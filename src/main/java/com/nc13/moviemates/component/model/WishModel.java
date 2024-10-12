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
public class WishModel {
    private Long id;
    private Long userId;
    private Long movieId;
}
