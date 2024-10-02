package com.nc13.moviemates.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name= "posters")
public class PosterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long movieId;
    private String url;
    private String filepath;
    private String filename;

    public PosterEntity(String posterPath, String uploadName) {
        this.filepath = posterPath;
        this.filename = uploadName;
    }
}
