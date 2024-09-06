package com.nc13.moviemates.service.impl;

import com.nc13.moviemates.model.domain.UserDomain;
import com.nc13.moviemates.model.entity.UserEntity;
import com.nc13.moviemates.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserService userService;
    @Override
    public List<UserEntity> findAll() {
        return userService.findAll();
    }

    @Override
    public UserEntity save(UserEntity email) {
        return userService.save(email);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userService.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userService.existsById(id);
    }

    @Override
    public Long count() {
        return userService.count();
    }

    @Override
    public Integer deleteById(Long id) {
        return userService.deleteById(id);
    }
}
