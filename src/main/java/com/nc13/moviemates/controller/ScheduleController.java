package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.OrderModel;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.service.ScheduleService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService service;


    @GetMapping
    public ResponseEntity<List<ScheduleEntity>> getList(){
        return ResponseEntity.ok(service.findAll());
    }

    //오더 누르면 상영정보와 영화관 리스트 불러오기
    @GetMapping("/order/{movieid}")
    public ResponseEntity<List<OrderModel>> findById(@PathVariable Long movieId){
        System.out.println("컨트롤러 진입!!");
        System.out.println(movieId);
        System.out.println(service.findByMovieId(movieId));
        return ResponseEntity.ok(service.findByMovieId(movieId));
    }

    @PostMapping
    public ResponseEntity<Boolean> insert (@RequestBody ScheduleEntity schedule){
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
