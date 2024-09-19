package com.nc13.moviemates.component.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class WishModel {
    private Long id;
    private Long userId;
    private Long movieId;
}
