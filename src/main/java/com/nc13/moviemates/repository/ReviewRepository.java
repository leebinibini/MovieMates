package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.ReviewEntity;
import com.nc13.moviemates.queryDsl.ReviewQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, ReviewQueryDSL {
}
