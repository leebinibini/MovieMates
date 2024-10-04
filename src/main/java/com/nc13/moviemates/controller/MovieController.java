package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.TheaterEntity;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.ScheduleService;
import com.nc13.moviemates.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService service;
    private final TheaterService theaterService;
    private final ScheduleService scheduleService;

    @GetMapping()
    public String toMovieAdmin(){
        return "admin/movie";
    }


    @GetMapping("/list")
    public ResponseEntity<List<MovieEntity>> getList() {
        return ResponseEntity.ok(service.findAll());
    }


    @GetMapping("/names")
    public ResponseEntity<List<String>> getNowPlayingList() {
        List<String> title = service.getNowPlayingList();
        return ResponseEntity.ok(title);
    }
    // 홈페이지 화면 리스트 가져오기


    @GetMapping("/order/{movieId}")
    public ResponseEntity<Map<String, Object>> getOrderList(@PathVariable Long movieId){
        Map<String, Object> map = new HashMap<>();
        String title = service.findById(movieId)
                        .orElseThrow(()-> new RuntimeException("Movie not found"))
                                .getTitle();

        map.put("theater", theaterService.findByMovieId(movieId));
        map.put("schedule", scheduleService.findByMovieId(movieId));
        map.put("title", title);
        return ResponseEntity.ok().body(map);
    }




    @GetMapping("/{id}")
    public ResponseEntity <Optional<MovieEntity>> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Long> insert (@RequestBody MovieModel movie){
        return ResponseEntity.ok(service.save(movie));
    }

//    @PutMapping
//    public ResponseEntity<Boolean> update(@RequestBody MovieEntity movie){
//        return ResponseEntity.ok(service.save(movie));
//    }

    @DeleteMapping("/{id}")
    public Boolean deleteById(@PathVariable Long id){
        return service.deleteById(id);
    }

    @GetMapping("/exists/{id}")
    public Boolean existsById(Long id) {
        return service.existsById(id);
    }

    @PostMapping("/findMovieIdByName")
    public ResponseEntity<Long> findMovieIdByName(@RequestParam("name") String name){

        return ResponseEntity.ok(service.findMovieIdByName(name));
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



