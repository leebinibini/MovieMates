package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.PosterEntity;
import com.nc13.moviemates.service.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Optional<PosterEntity>> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Boolean> insert (@RequestBody PosterEntity poster){
        return ResponseEntity.ok(service.save(poster));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody PosterEntity poster){
        return ResponseEntity.ok(service.save(poster));
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
