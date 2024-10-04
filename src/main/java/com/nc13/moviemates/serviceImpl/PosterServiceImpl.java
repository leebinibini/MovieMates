package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.model.PosterModel;
import com.nc13.moviemates.entity.PosterEntity;
import com.nc13.moviemates.repository.PosterRepository;
import com.nc13.moviemates.service.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PosterServiceImpl implements PosterService {
    private final PosterRepository repository;
    private final String STATIC_PATH = "/Users/jisue/Documents/my-project/monolithic/MovieMates_backend/src/main/resources/static/images";
    private final String POSTER_PATH = "/poster";

    @Override
    public List<PosterEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public String save(MultipartFile file) {
        //System.out.println("service도 진입!!");
        File pathDir = new File(POSTER_PATH);
        if (!pathDir.exists()) {
            new File(POSTER_PATH).mkdirs();
        }

        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String uploadName = UUID.randomUUID() + extension;
        String fullFilePath = STATIC_PATH + POSTER_PATH;

        PosterEntity posterEnt = new PosterEntity(fullFilePath, uploadName);

        File target = new File(fullFilePath, uploadName);

        try {
            // 파일 저장
            file.transferTo(target);  // MultipartFile로 전송된 파일을 저장
        } catch (IOException e) {
            e.printStackTrace();
        }

        repository.save(posterEnt);
        return fullFilePath + "/" + uploadName;
    }

    @Override
    public Optional<PosterEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean deleteById(Long id) {
         repository.deleteById(id);
        return !existsById(id);
    }
}
