package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.TheaterEntity;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.ScheduleService;
import com.nc13.moviemates.service.TheaterService;
import com.nc13.moviemates.serviceImpl.MovieServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService service;
    private final TheaterService theaterService;
    private final ScheduleService scheduleService;


    @GetMapping("/list")
    public ResponseEntity<List<MovieEntity>> getList() {
        return ResponseEntity.ok(service.findAll());
    }


    @GetMapping("/names")
    public ResponseEntity<List<String>> getNowPlayingList() {
        List<String> title = service.getNowPlayingList();
        return ResponseEntity.ok(title);
    }

    @GetMapping("/order/{movieId}")
    public ResponseEntity<Map<String, Object>> getOrderList(@PathVariable Long movieId){
        Map<String, Object> map = new HashMap<>();
        map.put("theater", theaterService.findByMovieId(movieId));
        map.put("schedule", scheduleService.findByMovieId(movieId));
        System.out.println(map);
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity <Optional<MovieEntity>> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/register")
    public String toMovieRegister(){
        return "admin/movie/register";
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Long> insert (@RequestBody MovieModel movie){
        System.out.println("영화등록 화면에서 넘어온 값 : "+ movie);
        return ResponseEntity.ok(service.save(movie));
    }

//    @PutMapping
//    public ResponseEntity<Boolean> update(@RequestBody MovieModel movie){
//        return ResponseEntity.ok(service.save(movie));
//    }

    @ResponseBody
    @PostMapping("/updateMany")
    public ResponseEntity<Boolean> updateByJspreadsheet(@RequestBody List<MovieModel> movieList) {
        System.out.println("영화 수정 컨트롤러 진입 성공!");
        System.out.println("영화리스트" + movieList);
        return ResponseEntity.ok(service.update(movieList));
    }

    @ResponseBody
    @PostMapping("/deleteMany")
    public ResponseEntity<Long> deleteMany(@RequestBody List<Long> movieIdList){
        return ResponseEntity.ok(service.deleteMany(movieIdList));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable long id){
        return ResponseEntity.ok(service.deleteById(id));
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

