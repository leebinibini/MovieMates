package com.nc13.moviemates.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@Table (name = "wishlists")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishEntity {
    @Id
    private Long id;
    private Long userId;
    private Long movieId;
}
