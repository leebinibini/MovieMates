package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.ReviewService;
import com.nc13.moviemates.service.ScheduleService;
import com.nc13.moviemates.service.TheaterService;
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
    private final ReviewService reviewService;


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
        System.out.println(movieId);
        String title = service.findById(movieId)
                        .orElseThrow(()-> new RuntimeException("Movie not found"))
                                .getTitle();

        map.put("theater", theaterService.findByMovieId(movieId));
        map.put("schedule", scheduleService.findByMovieId(movieId));
        map.put("title", title);
        System.out.println(map);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/single/{movieId}")
    public String getSingle(@PathVariable("movieId") Long movieId, Model model) {
        Optional<MovieModel> movie = service.findById(movieId); // movieId는 적절히 설정
        if (movie.isPresent()) {
            model.addAttribute("movie", movie.get());
        } else {
            // movie가 없을 경우 예외 처리 로직 추가 (예: 404 페이지로 리다이렉트 등)
            return "admin/404"; // 예시로 404 페이지 반환
        }

        model.addAttribute("theaterList", theaterService.findByMovieId(movieId));
        model.addAttribute("scheduleList", scheduleService.findByMovieId(movieId));
        model.addAttribute("reviewList", reviewService.findAllByMovieId(movieId));
        model.addAttribute("movieList", service.findAll());

        System.out.println(movie);



        System.out.println(theaterService.findByMovieId(movieId));
        System.out.println(scheduleService.findByMovieId(movieId));

        System.out.println(model);
        return "single";
    }


    @GetMapping("/{id}")
    public ResponseEntity <Optional<MovieModel>> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/register")
    public String toMovieRegister(Model model){
        model.addAttribute("movieList", service.findAll());
        model.addAttribute("theaterList", theaterService.findAll());
        return "admin/movie/register";
    }

    @GetMapping("/register2")
    public String toMovieRegister2(Model model){
        model.addAttribute("movieList", service.findAll());
        model.addAttribute("theaterList", theaterService.findAll());
        return "admin/movie/register2";
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Long> insert (@RequestBody MovieModel movie){
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

    @PostMapping("/findMovieIdByName")
    public ResponseEntity<Long> findMovieIdByName(@RequestParam("name") String name){

        return ResponseEntity.ok(service.findMovieIdByName(name));
    }


    public long count() {
        return service.count();}


    @PostMapping("/api/movie/like")
    public ResponseEntity<Map<String, Object>> likeMovie(@RequestBody Map<String, Object> payload) {
        Long movieId = ((Number) payload.get("movieId")).longValue();
        Boolean liked = (Boolean) payload.get("liked");

        // 영화 좋아요 처리 로직 (예: DB에 저장)
        boolean success = service.updateLikeStatus(movieId, liked);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);

        return ResponseEntity.ok(response);
    }


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

