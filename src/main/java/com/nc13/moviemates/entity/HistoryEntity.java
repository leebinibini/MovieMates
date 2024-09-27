package com.nc13.moviemates.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Getter @Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "historys")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String poster;
    private String room;
    private String title;
    private Date date;
    private Date time;
    private String seat;
    private Long reservations;
}
