package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.QHistoryEntity;
import com.nc13.moviemates.queryDsl.HistoryQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Long>, HistoryQueryDSL {

}
