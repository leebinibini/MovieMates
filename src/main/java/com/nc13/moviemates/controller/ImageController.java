package com.nc13.moviemates.controller;


import com.nc13.moviemates.component.model.ImageModel;
import com.nc13.moviemates.service.ImageService;
import com.nc13.moviemates.serviceImpl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageServiceImpl service;

    @GetMapping("/fileName")
    public ResponseEntity<String> getFileName(@RequestParam String fileName) {
        return ResponseEntity.ok(service.getFileName(fileName));
    }

    @PostMapping("/upload")
    public ResponseEntity<List<ImageModel>> uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles,
                                                        @RequestParam String uploadPath,@RequestParam Long postId) {
        return ResponseEntity.ok(service.uploadFiles(multipartFiles, uploadPath, postId));
    }
}
