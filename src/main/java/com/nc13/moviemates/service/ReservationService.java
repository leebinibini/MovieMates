package com.nc13.moviemates.service;

import com.nc13.moviemates.component.model.ReservationModel;
import com.nc13.moviemates.entity.ReservationEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    List<?> findAll();

   Optional<ReservationEntity> findById(Long id);

    Boolean save(ReservationEntity reservation);

    Boolean deleteById(Long id);

    Long count();

    Boolean existsById(Long id);

    Boolean update(List<ReservationModel> reservationList);

    Long deleteMany(List<Long> reservationIdList);
}
