package com.nc13.moviemates.model.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class WishModel {
    private Long userId;
    private Long movieId;
}
