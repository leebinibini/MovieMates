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
public class TheaterModel {
    private Long id;
    private String room;
    private String name;
    private String location;
    private int capacity;
}
