package com.nc13.moviemates.model.repository;

import com.nc13.moviemates.model.entity.UserEntity;
import com.nc13.moviemates.model.querydsl.UserQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, UserQueryDSL {
}
