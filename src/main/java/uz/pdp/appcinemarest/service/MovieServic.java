package uz.pdp.appcinemarest.service;

import org.springframework.http.HttpEntity;
import uz.pdp.appcinemarest.payload.MovieDto;

import java.util.UUID;

public interface MovieServic {
    HttpEntity getAllMovies(int page, int size, String search, String sort, boolean direction);

    HttpEntity getMovieById(Integer id);

    HttpEntity saveMovie(MovieDto movieDto);

    HttpEntity deleteMovie(Integer id);

}
