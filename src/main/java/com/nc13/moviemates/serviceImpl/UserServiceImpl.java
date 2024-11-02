package com.nc13.moviemates.serviceImpl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.nc13.moviemates.component.model.UserModel;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.repository.UserRepository;
import com.nc13.moviemates.service.UserService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final AmazonS3 amazonS3;
    private final PasswordEncoder passwordEncoder;



    @Transactional
    @Override
    public Boolean update(List<UserModel> userList) {
        System.out.println("user정보: " + userList);
        userList.forEach(user -> repository.update(user));

        return true;
    }

   /* @Override
    public Boolean update(UserModel userData) {
        System.out.println("user정보: " + userData);
        repository.update(userData);
        return true;
    }*/
    @Transactional
    @Override
    public Boolean deleteMany(List<Long> userIdList) {
        try {
            userIdList.forEach(userId -> {
                repository.deleteById(userId);
            });
            return true; // 모든 삭제 작업이 성공했을 때 true 반환
        } catch (Exception e) {
            return false; // 중간에 오류가 발생한 경우 false 반환
        }
    }



    @Override
    public List<?> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {

        return repository.findById(id);
    }

    @Override
    public Boolean existsByPassword(String password) {
        return repository.existsByPassword(password);
    }

    @Override
    public Boolean save(UserEntity user) {
        UserEntity ent = repository.save(user);
        Long id = ent.getId();
        return existsById(id);
    }

    @Override
    public UserEntity insert(UserEntity user){

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (user.getProfileImageUrl() == null) {
            user.setProfileImageUrl("https://moviemates-bucket.s3.ap-northeast-2.amazonaws.com/uploads/users/default_profile.png");
        }

        UserEntity savedUser = repository.save(user);
        System.out.println("회원가입 서비스 진입, ent: " + savedUser);
        Long id = savedUser.getId();
        if (id == null || !repository.existsById(id)) {
            throw new IllegalStateException("사용자 저장에 실패했습니다.");
        }

        return savedUser;
    }

    @Override
    public Boolean deleteById(Long id) {
        repository.deleteById(id);
        return !existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Value("${application.bucket.name}")
    private String bucketName;

    @Transactional
    @Override
    public Boolean updateUserInfo(UserModel userData, MultipartFile file) {

        // 유저 엔티티 조회
        UserEntity userEntity = repository.findById(userData.getId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 유저 ID입니다: " + userData.getId()));

        String profileImageUrl = userEntity.getProfileImageUrl();  // 기존 이미지 URL

        // 이미지 파일이 있을 경우 S3에 업로드하고 URL 업데이트
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String storedFileName = getFileName(originalFilename);

            // S3에 업로드할 경로
            String uploadPath = "uploads/users";
            String keyName = uploadPath + "/" + storedFileName;

            // 메타데이터 설정
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                // S3에 파일 업로드
                amazonS3.putObject(new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata));
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패: " + e.getMessage());
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }

            // 업로드된 이미지의 S3 URL
            profileImageUrl = "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + keyName;
        }

        // UserRepository에서 업데이트 수행
        repository.updateUserInfo(userData, profileImageUrl);

        return true;
    }

    @Override
    public UserEntity login(UserEntity user) {
        // 이메일로 유저를 찾음
        UserEntity foundUser = repository.findByEmail(user.getEmail());

        // 유저가 존재하고, 비밀번호가 일치하는지 확인
        if (foundUser != null && passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return foundUser;  // 로그인 성공
        }
        return null;  // 로그인 실패
    }

    @Override
    public UserEntity findByEmail(String email) {
        return repository.findByEmail(email);
    }

    // 파일 이름 생성 메서드
    private String getFileName(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf("."));
        return System.currentTimeMillis() + ext;  // 현재 시간을 이용한 고유 파일명 생성
    }


}
