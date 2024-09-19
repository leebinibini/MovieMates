package com.nc13.moviemates.service.impl;

import com.nc13.moviemates.Scraping.MovieSelenium;
import com.nc13.moviemates.model.domain.MovieDomain;
import com.nc13.moviemates.model.entity.MovieEntity;
import com.nc13.moviemates.model.repository.MovieRepository;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;
    private final MovieSelenium movieSelenium;

    @Override
    public List<MovieEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Boolean save(MovieEntity movie) {
       MovieEntity ent = repository.save(movie);
       Long id = ent.getId();
       return existsById(id);
    }

    @Override
    public Optional<MovieEntity> findById(Long id) {
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
    public void saveMovie(MovieDomain movieDomain) {

    }

    /*@Override
    public void saveMovie(MovieDomain movieDomain) {
        MovieEntity movieEntity = new MovieEntity(
                movieDomain.getTitle(),
                movieDomain.getReleaseDate(),
                movieDomain.getRunningTime(),
                movieDomain.getInformation(),
                movieDomain.getGenre(),
                movieDomain.getDirector()
        );

        // 데이터베이스에 저장
        repository.save(movieEntity);
    }*/

    public List<MovieEntity> saveMoviesBatch(List<MovieEntity> movies){
        List<MovieEntity> list = repository.saveAll(movies);
        return list;
    }

    public void crawlAndSaveMovies(String url) {
        List<MovieDomain> movies = movieSelenium.crawlMovies(url);
        for (MovieDomain movie : movies) {
            saveMovie(movie);
        }
    }
}
