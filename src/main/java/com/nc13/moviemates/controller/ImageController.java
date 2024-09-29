package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.ImageModel;
import com.nc13.moviemates.serviceImpl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {
    private final ImageServiceImpl service;
    //filename
    //upload
    @GetMapping("/fileName")
    public ResponseEntity<String> getFileName(@RequestPart String fileName) {
        return ResponseEntity.ok(service.getFileName(fileName));
    }

    @PostMapping("/upload/{movieId}")
    public ResponseEntity<Boolean> uploadFiles(
            @PathVariable long movieId, @RequestPart("multipartFiles") List<MultipartFile> multipartFiles) {
        System.out.println("이미지 컨트롤러 진입!");
        System.out.println("movieId: " + movieId);
        return ResponseEntity.ok(service.uploadFiles(movieId, multipartFiles));

    }
}