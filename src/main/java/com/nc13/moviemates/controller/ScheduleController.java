package com.nc13.moviemates.controller;

import com.nc13.moviemates.model.entity.PosterEntity;
import com.nc13.moviemates.model.entity.ScheduleEntity;
import com.nc13.moviemates.service.PosterService;
import com.nc13.moviemates.service.ScheduleService;
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

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleEntity> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<ScheduleEntity> insert (@RequestBody ScheduleEntity schedule){
        return ResponseEntity.ok(service.save(schedule));
    }

    @PutMapping
    public ResponseEntity<ScheduleEntity> update(@RequestBody ScheduleEntity schedule){
        return ResponseEntity.ok(service.save(schedule));
    }

    @DeleteMapping("/{id}")
    public Integer deleteById(@PathVariable Long id){
        return service.deleteById(id);
    }

    public boolean existsById(Long id) {
        return service.existsById(id);
    }

    public long count() {
        return service.count();}
}
