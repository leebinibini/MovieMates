package com.nc13.moviemates.model.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class WishModel {
    private Long id;
    private Long userId;
    private Long movieId;
}
