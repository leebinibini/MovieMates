package com.nc13.moviemates.controller;
import com.nc13.moviemates.component.model.ScheduleModel;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.entity.SeatEntity;
import com.nc13.moviemates.service.ScheduleService;
import com.nc13.moviemates.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/seat")
public class SeatController {
    private final SeatService service;
    private final ScheduleService scheduleService;

    @GetMapping("/{theaterId}/{scheduleId}")
    public ResponseEntity<List<SeatEntity>> getSeats(@PathVariable("theaterId") Long theaterId,
                                                     @PathVariable("scheduleId") Long scheduleId){

        List<SeatEntity> seats = service.getSeatsByTheaterAndSchedule(theaterId, scheduleId);
        System.out.println("w좌석 가져간닝"+seats);
        return ResponseEntity.ok(seats);

    }
}
