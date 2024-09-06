package com.nc13.moviemates.controller;

import com.nc13.moviemates.model.entity.MovieEntity;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService service;


    @GetMapping
    public ResponseEntity<List<MovieEntity>> getList(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieEntity> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<MovieEntity> insert (@RequestBody MovieEntity user){
        return ResponseEntity.ok(service.save(user));
    }

    @PutMapping
    public ResponseEntity<MovieEntity> update(@RequestBody MovieEntity user){
        return ResponseEntity.ok(service.save(user));
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


