package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.PosterEntity;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.service.PosterService;
import com.nc13.moviemates.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService service;


    @GetMapping("/list")
    public ResponseEntity<List<ScheduleEntity>> getList(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity <Optional<ScheduleEntity>> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @ResponseBody
    @PostMapping("/register")
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
