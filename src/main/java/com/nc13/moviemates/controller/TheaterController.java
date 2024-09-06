package com.nc13.moviemates.controller;

import com.nc13.moviemates.model.entity.TheaterEntity;
import com.nc13.moviemates.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/theaters")
public class TheaterController {
    private final TheaterService service;


    @GetMapping
    public ResponseEntity<List<TheaterEntity>> getList(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheaterEntity> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<TheaterEntity> insert (@RequestBody TheaterEntity theater){
        return ResponseEntity.ok(service.save(theater));
    }

    @PutMapping
    public ResponseEntity<TheaterEntity> update(@RequestBody TheaterEntity theater){
        return ResponseEntity.ok(service.save(theater));
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
