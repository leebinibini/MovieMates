package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.model.UserModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.repository.UserRepository;
import com.nc13.moviemates.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;


    @Override
    public boolean authenticate(String email, String password) {
        System.out.println("서비스 진입 완료!");
        UserEntity user = repository.findByEmail(email);

        if (user != null && password.equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(UserModel userData) {
        System.out.println("user정보: " + userData);
        repository.update(userData);
        return true;
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
    public Boolean insert(UserEntity user){

        UserEntity ent = repository.save(user);
        System.out.println("서비스 진입, ent: " + ent);
        Long id = ent.getId();
        return repository.existsById(id);
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



}
