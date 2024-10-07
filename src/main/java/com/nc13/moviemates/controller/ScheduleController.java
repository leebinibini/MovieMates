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
            // 삼항 연산자 사용
            return service.saveSchedule(scheduleForm)
                    ? ResponseEntity.ok("스케줄 등록 성공!")
                    : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스케줄 등록 실패");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청: " + e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/updateMany")
    public ResponseEntity<Boolean> updateByJspreadsheet (@RequestBody List<ScheduleModel> scheduleList){
        System.out.println("상영스케줄 업데이트 컨트롤러 진입");
        System.out.println(scheduleList);
        return ResponseEntity.ok(service.update(scheduleList));
    }

//    @PutMapping
//    public ResponseEntity<Boolean> update(@RequestBody ScheduleEntity schedule){
//        return ResponseEntity.ok(service.save(schedule));
//    }

    @ResponseBody
    @PostMapping("/deleteMany")
    public ResponseEntity<Boolean> deleteMany(@RequestBody List<Long> scheduleIdList){
        return ResponseEntity.ok(service.deleteMany(scheduleIdList));
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
