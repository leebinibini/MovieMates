package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.WishEntity;
import com.nc13.moviemates.queryDsl.WishQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Long>, WishQueryDSL {
}
