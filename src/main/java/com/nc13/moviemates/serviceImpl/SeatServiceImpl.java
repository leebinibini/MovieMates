package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.entity.SeatEntity;
import com.nc13.moviemates.repository.SeatRepository;
import com.nc13.moviemates.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository repository;

    @Override
    public List<SeatEntity> getAll() {
        return repository.getAll();
    }

    @Override
    public SeatEntity getById() {
        return null;
    }

    @Override
    public Boolean exists(Long id) {
        return repository.exists(id);
    }

    @Override
    public Long getRowCount(Long id) {
        return repository.getRowCount();
    }

    @Override
    public List<SeatEntity> findSeatsByScheduleId(Long scheduleId) {
        return repository.findSeatsByScheduleId(scheduleId);
    }


}
