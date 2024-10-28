package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.UserModel;
import com.nc13.moviemates.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UserService {
    List<?> findAll();

    Optional<UserEntity> findById(Long id);

    Boolean existsByPassword(String password);

    Boolean save(UserEntity user);

    Boolean deleteById(Long id);

    Long count();

    Boolean existsById(Long id);

    UserEntity insert(UserEntity user);

    //Boolean update(UserModel userData);
    Boolean update(List<UserModel> userList);

    Boolean deleteMany(List<Long> userIdList);

    Boolean updateUserInfo(UserModel userData, MultipartFile file);

    UserEntity login(UserEntity user);

    UserEntity findByEmail(String email);
}
