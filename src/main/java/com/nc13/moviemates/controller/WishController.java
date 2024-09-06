package com.nc13.moviemates.controller;

import com.nc13.moviemates.model.entity.WishEntity;
import com.nc13.moviemates.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/wishes")
public class WishController {
    @Qualifier("wishServiceImpl")
    private final WishService service;

    @GetMapping()
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<WishEntity>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Boolean> insert(@RequestBody WishEntity wishList) {
        return ResponseEntity.ok(service.save(wishList));
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
