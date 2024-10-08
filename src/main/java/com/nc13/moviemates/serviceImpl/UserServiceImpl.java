package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.model.UserModel;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.repository.UserRepository;
import com.nc13.moviemates.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Boolean update(List<UserModel> userList) {
        System.out.println("user정보: " + userList);
        userList.forEach(user -> repository.update(user));

        return true;
    }

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
    public Boolean save(UserEntity user) {
        UserEntity ent = repository.save(user);
        Long id = ent.getId();
        return existsById(id);
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
