package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.component.model.TheaterModel;
import com.nc13.moviemates.entity.TheaterEntity;
import com.nc13.moviemates.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/theater")
public class TheaterController {
    private final TheaterService service;

    @GetMapping()
    public String toTheaterAdmin(){
        return "admin/theater/list";
    }


    @GetMapping("/list")
    public ResponseEntity<List<TheaterEntity>> getList(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TheaterEntity>> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/register")
    public String toTheaterRegister (){
        return "admin/theater/register";
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Boolean> insert (@RequestBody TheaterEntity theater){
        System.out.println("화면에서 넘어오는 극장 정보: " + theater);
        return ResponseEntity.ok(service.save(theater));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody TheaterEntity theater){
        return ResponseEntity.ok(service.save(theater));
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody List<TheaterModel> theaterList) {
        System.out.println("극장 수정 컨트롤러 진입 성공!");
        System.out.println("극장리스트" + theaterList);
        return ResponseEntity.ok(service.update(theaterList));
    }

    @ResponseBody
    @PostMapping("/deleteMany")
    public ResponseEntity<Long> deleteMany(@RequestBody List<Long> theaterIdList){
        return ResponseEntity.ok(service.deleteMany(theaterIdList));
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
