package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService service;


    @GetMapping("/names")
    public ResponseEntity<List<String>> getNowPlayingList() {
        List<String> title = service.getNowPlayingList();
        return ResponseEntity.ok(title);
    }
    // 홈페이지 화면 리스트 가져오기




    @GetMapping("/{id}")
    public ResponseEntity <Optional<MovieEntity>> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> insert (@RequestBody MovieEntity movie){
        return ResponseEntity.ok(service.save(movie));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody MovieEntity movie){
        return ResponseEntity.ok(service.save(movie));
    }

    @DeleteMapping("/{id}")
    public Boolean deleteById(@PathVariable Long id){
        return service.deleteById(id);
    }

    @GetMapping("/exists/{id}")
    public Boolean existsById(Long id) {
        return service.existsById(id);
    }


    public long count() {
        return service.count();}


    /*@GetMapping("/crawl")
    public String crawlMovies() {
        try {
            service.crawlMovies();
            return "Crawling complete!";
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }*/
}



