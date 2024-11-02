package com.nc13.moviemates.serviceImpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.nc13.moviemates.component.model.ImageModel;
import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.config.BucketConfig;
import com.nc13.moviemates.component.model.MovieModel;;
import com.nc13.moviemates.entity.ImageEntity;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.repository.ImageRepository;
import com.nc13.moviemates.repository.MovieRepository;
import com.nc13.moviemates.service.ImageService;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository repository;
    private final MovieServiceImpl movieService;
    private final MovieRepository movieRepository;
    private final AmazonS3 amazonS3;
    String uploadPath = "uploads/movies";
    // 버킷경로 가져오기
    @Value("${application.bucket.name}")
    private String bucketName;

    // 확장자 뽑아내기
    @Override
    public String getFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf("."));
        return System.currentTimeMillis() + ext;
    }

    @Override
    public Boolean uploadFiles(long movieId, List<MultipartFile> multipartFiles) {
        // 파일 저장 공간을 리스트 형태로 만들겠다!
        List<ImageModel> s3files = new ArrayList<>();

        MovieEntity movieEntity = movieService.findEntityById(movieId);
        if (movieEntity == null) {
            throw new IllegalArgumentException("Invalid movieId: " + movieId);
        }

        // Prepare a list to store updated MovieModel objects
        List<MovieModel> updatedMovies = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFilename = multipartFile.getOriginalFilename();
            String storedFileName = getFileName(originalFilename);
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            }
            String uploadURL = "";

            // Set metadata for AmazonS3 upload
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                String keyName = uploadPath + "/" + storedFileName;

                amazonS3.putObject(
                        new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata));
                System.out.println("keyName = 은!!!!" + keyName);

                uploadURL = "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + keyName;
                System.out.println("uploadURL = " + uploadURL);
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
            }

            ImageModel imageModel = ImageModel.builder()
                    .originalFilename(originalFilename)
                    .storedFileName(storedFileName)
                    .extension(extension)
                    .uploadPath(uploadPath)
                    .uploadURL(uploadURL)
                    .movieId(movieEntity.getId())
                    .build();

            ImageEntity imageEntity = convertToEntity(imageModel);

            ImageEntity savedEntity = repository.save(imageEntity);
            imageModel.setId(savedEntity.getId());
            s3files.add(imageModel);

            // Update movie's posterUrl and prepare MovieModel for update
            movieEntity.setWidthPosterUrl(uploadURL);
            movieEntity.setLengthPosterUrl(uploadURL);

            // Convert MovieEntity to MovieModel
            MovieModel movieModel = convertEntityToModel(movieEntity);
            updatedMovies.add(movieModel);

            System.out.println("Poster URL 업데이트됨: " + uploadURL);
        }

        // Call movieService.update to update the list of movies
        movieService.update(updatedMovies);

        return !s3files.isEmpty();
    }

    private ImageEntity convertToEntity(ImageModel model) {
        MovieEntity movieEntity = movieService.findEntityById(model.getMovieId());
        System.out.println("convertToEntity의 movieEntity : " + movieEntity);

        return ImageEntity.builder()
                .originalFileName(model.getOriginalFilename())
                .storedFileName(model.getStoredFileName())
                .extension(model.getExtension())
                .uploadPath(model.getUploadPath())
                .uploadURL(model.getUploadURL())
                .build();
    }

    private MovieModel convertEntityToModel(MovieEntity movieEntity) {
        return MovieModel.builder()
                .id(movieEntity.getId())
                .title(movieEntity.getTitle())
                .director(movieEntity.getDirector())
                .actors(movieEntity.getActors())
                .rate(movieEntity.getRate())
                .releaseDate(movieEntity.getReleaseDate())
                .runningTime(movieEntity.getRunningTime())
                .ageClass(movieEntity.getAgeClass())
                .plot(movieEntity.getPlot())
                .genre(movieEntity.getGenre())
                .widthPosterUrl(movieEntity.getWidthPosterUrl())
                .lengthPosterUrl(movieEntity.getLengthPosterUrl())
                .isShowing(movieEntity.getIsShowing())
                .booking(movieEntity.getBooking())
                .build();
    }
}
