package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.proxy.MovieSelenium;
import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.repository.MovieRepository;
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
    public Boolean save(MovieModel movie) {
        MovieEntity ent = Optional.of(new MovieEntity())
                .map(e -> {
                    e.setTitle(movie.getTitle());
                    e.setInformation(movie.getInformation());
                    return e;
                }).orElseThrow();

        MovieEntity savedEntity = repository.save(ent);

        return existsById(savedEntity.getId());
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

    public void saveMovie(MovieModel movieDomain) {

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
        List<MovieModel> movies = movieSelenium.crawlMovies(url);
        for (MovieModel movie : movies) {
            saveMovie(movie);
        }
    }
}
