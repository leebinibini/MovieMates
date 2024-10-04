package com.nc13.moviemates.component.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageModel {
    private Long id;
    private String originalFilename;
    private String storedFileName;
    private String extension;
    private String uploadPath; // storage 내에 파일이 저장된 경로
    private String uploadURL; // storage에서 파일에 접근할 수 있는 URL
    private Long movieId;
}