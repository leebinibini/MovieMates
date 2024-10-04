package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.component.model.OrderModel;
import com.nc13.moviemates.component.model.ScheduleModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.entity.TheaterEntity;
import com.nc13.moviemates.repository.MovieRepository;
import com.nc13.moviemates.repository.ScheduleRepository;
import com.nc13.moviemates.repository.TheaterRepository;
import com.nc13.moviemates.service.ScheduleService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository repository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;

    @Override
    public List<ScheduleEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Boolean save(ScheduleEntity schedule) {
        ScheduleEntity ent = repository.save(schedule);
        Long id = ent.getId();
        return existsById(id);
    }

    @Override
    public List<OrderModel> findOrderByMovieId(Long movieId) {
        return repository.findOrderByMovieId(movieId);
    }

    @Override
    public List<ScheduleEntity> findByMovieId(Long movieId) {
        return repository.findByMovieId(movieId);
    }


    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean deleteById(Long id) {
        repository.deleteById(id);
        return !existsById(id);
    }

    @Override
    public Boolean saveSchedule(ScheduleModel schedule, String inputMovie, String inputTheater) {
        System.out.println("스케줄 serviceImpl 진입!");
        // 영화 제목으로 movieId 조회
        MovieEntity movie = movieRepository.findByTitle(inputMovie)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화 제목을 찾을 수 없습니다: " + inputMovie));

        // 극장 이름으로 theaterId 조회
        TheaterEntity theater = theaterRepository.findByName(inputTheater)
                .orElseThrow(() -> new IllegalArgumentException("해당 극장 이름을 찾을 수 없습니다: " + inputTheater));

        // 스케줄 생성 및 저장
        ScheduleEntity ent = ScheduleEntity.builder()
                .movieId(movie.getId())
                .theaterId(theater.getId())
                .showDate(schedule.getShowDate())
                .showTime(schedule.getShowTime())
                .build();
        System.out.println(ent);
        // 스케줄 저장
        repository.save(ent);
        Long id = ent.getId();
        return (existsById(id));
    }
}
