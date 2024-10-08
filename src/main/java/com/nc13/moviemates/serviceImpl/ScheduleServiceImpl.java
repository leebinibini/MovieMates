package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.model.OrderModel;
import com.nc13.moviemates.component.model.ScheduleModel;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.repository.MovieRepository;
import com.nc13.moviemates.repository.ScheduleRepository;
import com.nc13.moviemates.repository.TheaterRepository;
import com.nc13.moviemates.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
    public ScheduleEntity findSchedule(Long theaterId, Long movieId, LocalDate showDate, LocalTime showTime) {
        return repository.findScheduleEntity(theaterId, movieId, showDate, showTime);
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
    public Boolean deleteMany(List<Long> scheduleIdList) {
        scheduleIdList.forEach(id -> {
            repository.deleteById(id);
        });
        return true;
    }

    @Override
    public Boolean saveSchedule(Map<String, String> scheduleForm) {
        System.out.println("스케줄 serviceImpl 진입!");
        Long movieId = Long.valueOf(scheduleForm.get("inputMovie"));
        Long theaterId = Long.valueOf(scheduleForm.get("inputTheater"));

//        // 영화 제목으로 movieId 조회
//        MovieEntity movie = movieRepository.findByTitle(scheduleForm.get("inputMovie"))
//                .orElseThrow(() -> new IllegalArgumentException("해당 영화 제목을 찾을 수 없습니다"));
//
//        // 극장 이름으로 theaterId 조회
//        TheaterEntity theater = theaterRepository.findByName(scheduleForm.get("inputTheater"))
//                .orElseThrow(() -> new IllegalArgumentException("해당 극장 이름을 찾을 수 없습니다"));

        // showDate와 showTime을 문자열에서 LocalDate 및 LocalTime으로 변환
        String inputShowDate = scheduleForm.get("inputShowDate");  // "yyyy-MM-dd" 형식
        String inputShowTime = scheduleForm.get("inputShowTime");  // "HH:mm" 형식

        // 날짜 및 시간 형식 지정
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // LocalDate와 LocalTime으로 변환
        LocalDate showDate = LocalDate.parse(inputShowDate, dateFormatter);
        LocalTime showTime = LocalTime.parse(inputShowTime, timeFormatter);

        // 스케줄 생성 및 저장
        ScheduleEntity ent = ScheduleEntity.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .showDate(showDate)
                .showTime(showTime)
                .build();
        System.out.println(ent);
        // 스케줄 저장
        repository.save(ent);
        Long id = ent.getId();
        return (existsById(id));
    }

    @Override
    public Boolean update(List<ScheduleModel> scheduleList) {
        System.out.println("상영스케줄 업데이트 서비스 진입");

        scheduleList.forEach(schedule -> {
            // ScheduleEntity에 변환된 값 설정
            ScheduleEntity ent = ScheduleEntity.builder()
                    .id(schedule.getId())
                    .movieId(schedule.getMovieId())
                    .theaterId(schedule.getTheaterId())
                    .showDate(schedule.getShowDate())   // LocalDate 설정
                    .showTime(schedule.getShowTime())   // LocalTime 설정
                    .build();

            // 엔티티 저장 또는 업데이트 로직 추가
            repository.save(ent);  // 이미 존재하는 경우 업데이트가 될 수 있게 처리
        });

        return true;  // 필요한 경우 업데이트 성공 여부 반환
    }
}
