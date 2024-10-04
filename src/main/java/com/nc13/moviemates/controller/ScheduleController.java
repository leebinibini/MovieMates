package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.ScheduleModel;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService service;


    @GetMapping("/list")
    public ResponseEntity<List<ScheduleEntity>> getList(){
        return ResponseEntity.ok(service.findAll());
    }

    /*//오더 누르면 상영정보와 영화관 리스트 불러오기
    @GetMapping("/order/{movieid}")
    public ResponseEntity<List<OrderModel>> findById(@PathVariable Long movieId){
        System.out.println("컨트롤러 진입!!");
        System.out.println(movieId);
        System.out.println(service.findByMovieId(movieId));
        return ResponseEntity.ok(service.findByMovieId(movieId));
    }*/

    @GetMapping("/register")
    public String toScheduleRegister(){
        return "admin/schedule/register";
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<String> insert (@RequestBody Map<String, String> scheduleForm){
        System.out.println("상영정보 등록 컨트롤러 진입!!!!");
        System.out.println("schedulForm: " + scheduleForm);
        try {
            // 프론트엔드에서 넘어온 데이터 파싱
            String inputMovie = scheduleForm.get("inputMovie");
            String inputTheater = scheduleForm.get("inputTheater");
            String inputShowDate = scheduleForm.get("inputShowDate");
            String inputShowTime = scheduleForm.get("inputShowTime");

            // 날짜 형식 설정
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

// LocalDate와 LocalTime으로 변환
            LocalDate showDate = LocalDate.parse(inputShowDate, dateFormatter);
            LocalTime showTime = LocalTime.parse(inputShowTime, timeFormatter);

// LocalDate -> LocalDateTime 변환 (시간 00:00:00으로 고정)
            LocalDateTime showDateWithTimeZero = showDate.atStartOfDay();

// LocalTime -> LocalDateTime 변환 (임의의 날짜와 결합)
            LocalDateTime showTimeWithDate = LocalDateTime.of(LocalDate.of(1970, 1, 1), showTime); // 1970년 1월 1일 기준으로 시간만 설정

// LocalDateTime -> Instant -> Date 변환
            Date showDateAsDate = Date.from(showDateWithTimeZero.atZone(ZoneId.systemDefault()).toInstant());
            Date showTimeAsDate = Date.from(showTimeWithDate.atZone(ZoneId.systemDefault()).toInstant());

// ScheduleModel에 설정
            ScheduleModel schedule = new ScheduleModel();
            schedule.setShowDate(showDateAsDate); // 날짜만 포함된 Date
            schedule.setShowTime(showTimeAsDate); // 시간만 포함된 Dat

            System.out.println(schedule);
            // Schedule 저장
            service.saveSchedule(schedule, inputMovie, inputTheater);

            return ResponseEntity.ok("스케줄 등록 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스케줄 등록 실패: " + e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/update")
    public ResponseEntity<Boolean> updateByJspreadsheet (@RequestBody ScheduleEntity schedule){
        System.out.println(schedule);
        return ResponseEntity.ok(service.save(schedule));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody ScheduleEntity schedule){
        return ResponseEntity.ok(service.save(schedule));
    }

    @DeleteMapping("/{id}")
    public Boolean deleteById(@PathVariable Long id){
        return service.deleteById(id);
    }

    public Boolean existsById(Long id) {
        return service.existsById(id);
    }

    public long count() {
        return service.count();}

}
