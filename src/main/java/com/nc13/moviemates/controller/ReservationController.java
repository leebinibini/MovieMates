package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.ReservationEntity;
import com.nc13.moviemates.service.HistoryService;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    @Qualifier("reservationServiceImpl")
    private final ReservationService service;

    @GetMapping()
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
}
