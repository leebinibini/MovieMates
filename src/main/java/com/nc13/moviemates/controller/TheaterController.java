package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.TheaterEntity;
import com.nc13.moviemates.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/theater")
public class TheaterController {
    private final TheaterService service;

    @GetMapping("/findTheaterIdByName")
    public ResponseEntity<Long> findTheaterIdByName(@RequestParam("name") String name ){
        System.out.println("관 아이디:"+service.findTheaterIdByName(name));
        return ResponseEntity.ok(service.findTheaterIdByName(name));

    }

    @GetMapping
    public ResponseEntity<List<TheaterEntity>> getList(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TheaterEntity>> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Boolean> insert (@RequestBody TheaterEntity theater){
        return ResponseEntity.ok(service.save(theater));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody TheaterEntity theater){
        return ResponseEntity.ok(service.save(theater));
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
