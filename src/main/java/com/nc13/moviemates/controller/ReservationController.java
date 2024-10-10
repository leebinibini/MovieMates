package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.ReservationModel;
import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.ReservationEntity;
import com.nc13.moviemates.service.HistoryService;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.RegEx;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService service;

    @GetMapping(("/list"))
    public ResponseEntity<List<ReservationEntity>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ReservationEntity>> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Boolean> insert(@RequestBody ReservationModel reservation) {
        return ResponseEntity.ok(service.save(reservation));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody ReservationModel reservation) {
        return ResponseEntity.ok(service.save(reservation));
    }

    @ResponseBody
    @PostMapping("/updateMany")
    public ResponseEntity<Boolean> updateByJspreadSheet(@RequestBody List<ReservationModel> reservationList) {
        System.out.println("예매 정보 수정 컨트롤러 진입 성공!");
        System.out.println("예매리스트" + reservationList);
        return ResponseEntity.ok(service.update(reservationList));
    }

    @ResponseBody
    @PostMapping("/deleteMany")
    public ResponseEntity<Long> deleteMany(@RequestBody List<Long> reservationIdList) {
        return ResponseEntity.ok(service.deleteMany(reservationIdList));
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


}
