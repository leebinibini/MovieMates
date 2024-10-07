package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.ReservationModel;
import com.nc13.moviemates.entity.ReservationEntity;
import com.nc13.moviemates.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService service;

    @GetMapping("/list")
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ReservationEntity>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Boolean> insert(@RequestBody ReservationEntity reservation) {
        return ResponseEntity.ok(service.save(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @GetMapping("/existsById/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }

    @ResponseBody
    @PostMapping("/updateMany")
    public ResponseEntity<Boolean> updateByJspreadsheet(@RequestBody List<ReservationModel> reservationList) {
        System.out.println("영화 수정 컨트롤러 진입 성공!");
        System.out.println("영화리스트" + reservationList);
        return ResponseEntity.ok(service.update(reservationList));
    }

    @ResponseBody
    @PostMapping("/deleteMany")
    public ResponseEntity<Long> deleteMany(@RequestBody List<Long> reservationIdList){
        return ResponseEntity.ok(service.deleteMany(reservationIdList));
    }

}
