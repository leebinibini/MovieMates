package com.nc13.moviemates.controller;


import com.nc13.moviemates.model.domain.UserDomain;
import com.nc13.moviemates.model.entity.UserEntity;
import com.nc13.moviemates.model.repository.UserRepository;
import com.nc13.moviemates.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;


    @GetMapping
    public ResponseEntity<List<UserEntity>> getList(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<UserEntity> insert (@RequestBody UserEntity user){
        return ResponseEntity.ok(service.save(user));
    }

    @PutMapping
    public ResponseEntity<UserEntity> update(@RequestBody UserEntity user){
        return ResponseEntity.ok(service.save(user));
    }

    @DeleteMapping("/{id}")
    public Integer deleteById(@PathVariable Long id){
        return service.deleteById(id);
    }

    public boolean existsById(Long id) {
        return service.existsById(id);
    }

    public long count() {
        return service.count();}}

