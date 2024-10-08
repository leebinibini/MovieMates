package com.nc13.moviemates.controller;
import com.nc13.moviemates.entity.SeatEntity;
import com.nc13.moviemates.service.ScheduleService;
import com.nc13.moviemates.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seat")
public class SeatController {
    private final SeatService service;
    private final ScheduleService scheduleService;

    @ResponseBody
    @PostMapping("/map")
    public ResponseEntity<?> getSeats(@RequestBody Map<String, Long> request){
        List<SeatEntity> seats = service.findSeatsByScheduleId(request.get("scheduleId"));
        System.out.println(request);
        System.out.println("해당 영화 좌석 가져가기 성공!:" + seats);

        Map<String, Object> response = new HashMap<>();
        response.put("seats", seats);
        return ResponseEntity.ok(response);
    }
}
