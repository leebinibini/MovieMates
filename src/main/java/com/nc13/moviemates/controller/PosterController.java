package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.PosterModel;
import com.nc13.moviemates.entity.PosterEntity;
import com.nc13.moviemates.service.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/poster")
public class PosterController {
    private final PosterService service;


    @GetMapping("/list")
    public ResponseEntity<List<PosterEntity>> getList(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PosterEntity>> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> insert (@RequestBody MultipartFile file){
        System.out.println("컨트롤러 진입!!");
        return ResponseEntity.ok(service.save(file));
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
