package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.ImageModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    String getFileName(String fileName);

    Boolean uploadFiles(long movieId, List<MultipartFile> multipartFiles);
}
