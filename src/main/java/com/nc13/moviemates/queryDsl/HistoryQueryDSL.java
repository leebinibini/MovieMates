package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.entity.HistoryEntity;

import java.util.List;
import java.util.Optional;

public interface HistoryQueryDSL {


    List<HistoryEntity> getAll();

    Optional<HistoryEntity> findById(Long id);

    Boolean exists(Long id);

    Long getRowCount();
}
