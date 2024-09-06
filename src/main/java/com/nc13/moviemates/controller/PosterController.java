package com.nc13.moviemates.controller;

import com.nc13.moviemates.model.entity.MovieEntity;
import com.nc13.moviemates.model.entity.PosterEntity;
import com.nc13.moviemates.service.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/posters")
public class PosterController {
    private final PosterService service;


    @GetMapping
    public ResponseEntity<List<PosterEntity>> getList(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PosterEntity> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<PosterEntity> insert (@RequestBody PosterEntity poster){
        return ResponseEntity.ok(service.save(poster));
    }

    @PutMapping
    public ResponseEntity<PosterEntity> update(@RequestBody PosterEntity poster){
        return ResponseEntity.ok(service.save(poster));
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
