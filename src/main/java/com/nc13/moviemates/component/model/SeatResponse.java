package com.nc13.moviemates.component.model;

import com.nc13.moviemates.entity.SeatEntity;
import lombok.Data;

import java.util.List;

@Data
public class SeatResponse {
 private List<SeatEntity> seats;
}

