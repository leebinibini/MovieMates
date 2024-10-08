package com.nc13.moviemates.controller;
import com.nc13.moviemates.component.model.SeatResponse;
import com.nc13.moviemates.component.model.ScheduleModel;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.entity.SeatEntity;
import com.nc13.moviemates.service.ScheduleService;
import com.nc13.moviemates.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat")
public class SeatController {
    private final SeatService service;
    private final ScheduleService scheduleService;

    @PostMapping("/")
    public ResponseEntity<?> getSeats(@RequestBody ScheduleModel scheduleModel){
        Long theaterId = scheduleModel.getTheaterId();
        Long movieId = scheduleModel.getMovieId();
        LocalDate showDate = scheduleModel.getShowDate();
        LocalTime showTime = LocalTime.from(scheduleModel.getShowTime());

        ScheduleEntity schedule = scheduleService.findSchedule(theaterId, movieId, showDate, showTime);
        if (schedule == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 상영 일정을 찾을 수 없습니다.");
        }
        List<SeatEntity> seats = service.findSeatsByScheduleId(schedule.getId());
        System.out.println("해당 영화 좌석 가져가기 성공!:" + seats);
        return ResponseEntity.ok(seats);
    }
}
