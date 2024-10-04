package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.model.TheaterModel;
import com.nc13.moviemates.entity.TheaterEntity;
import com.nc13.moviemates.repository.TheaterRepository;
import com.nc13.moviemates.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheaterServiceImpl implements TheaterService{
    private final TheaterRepository repository;

    @Override
    public List<TheaterEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Boolean save(TheaterEntity theater) {
         TheaterEntity ent = repository.save(theater);
         Long id =  ent.getId();
        return existsById(id);
    }

    @Override
    public Optional<TheaterEntity> findById(Long id) {
        return repository.findById(id);
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
    public List findByMovieId(Long movieId) {
        return repository.findByMovieId(movieId);
    }

    @Override
    @Transactional
    public Long deleteMany(List<Long> theaterIdList) {
        return repository.deleteMany(theaterIdList);
    }


    @Override
    @Transactional
    public Boolean update(List<TheaterModel> theaterList) {
        System.out.println("극장서비스 컨트롤러 진입 성공!");
        theaterList.forEach(theater -> repository.update(theater));
        return true;
    }

    @Override
    public Long findTheaterIdByName(String name) {
        return repository.findTheaterIdByName(name);
    }

}
