package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.queryDsl.UserQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, UserQueryDSL {
    UserEntity findByEmailAndPassword(String email, String password);
}
