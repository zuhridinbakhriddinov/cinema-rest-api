package uz.pdp.appcinemarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appcinemarest.entity.*;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.payload.MovieDto;
import uz.pdp.appcinemarest.projection.CustomMovie;
import uz.pdp.appcinemarest.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Zuhridin Bakhriddinov 3/16/2022 11:29 AM
@Service
public class MovieService implements MovieServic {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    DirectorRepository directorRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    DistributorRepository distributorRepository;
    @Autowired
    ActorRepository actorRepository;

    @Autowired
    AttachmentService attachmentService;

    @Override
    public HttpEntity getAllMovies(int page, int size, String search, String sort, boolean direction) {
        Pageable pageable = PageRequest.of(
                page - 1,
                size,
                direction ? Sort.Direction.ASC : Sort.Direction.DESC,
                sort);
        try {
            Page<CustomMovie> all = movieRepository.findAllByPage(
                    pageable,
                    search);
            return ResponseEntity.ok(new ApiResponse("success", true, all.getContent()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("error", false, null));
        }

    }

    @Override
    public HttpEntity getMovieById(Integer id) {
        return null;
    }

    @Override
    public HttpEntity saveMovie(MovieDto movieDto) {


        Movie movie = new Movie();


        Optional<Distributor> distributorOptional = distributorRepository.findById(movieDto.getDistributorId());
        if (!distributorOptional.isPresent()) {
            return new ResponseEntity(new ApiResponse("wrong",
                    false, null), HttpStatus.BAD_REQUEST);
        }

        ArrayList<Director> directors = new ArrayList<>();
        for (Integer directorId : movieDto.getDirectorIds()) {
            if (!directorRepository.findById(directorId).isPresent()) {
                return new ResponseEntity(new ApiResponse("wrong",
                        false, null), HttpStatus.BAD_REQUEST);
            }
            directors.add(directorRepository.findById(directorId).get());
        }

        ArrayList<Genre> genres = new ArrayList<>();
        for (Integer genreId : movieDto.getGenreIds()) {
            if (!genreRepository.findById(genreId).isPresent()) {
                return new ResponseEntity(new ApiResponse("wrong",
                        false, null), HttpStatus.BAD_REQUEST);
            }
            genres.add(genreRepository.findById(genreId).get());
        }
        ArrayList<Actor> actors = new ArrayList<>();
        for (Integer actorId : movieDto.getGenreIds()) {
            if (!actorRepository.findById(actorId).isPresent()) {
                return new ResponseEntity(new ApiResponse("wrong",
                        false, null), HttpStatus.BAD_REQUEST);
            }
            actors.add(actorRepository.findById(actorId).get());
        }

        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setDurationInMinutes(movieDto.getDurationInMinutes());
        movie.setMinPrice(movieDto.getMinPrice());
        movie.setDistributorShareInPercent(movieDto.getDistributorShareInPercent());
        movie.setCoverImage(attachmentService.saveAttachment(movieDto.getCoverImage()));
        movie.setTrailerVideo(attachmentService.saveAttachment(movieDto.getTrailerVideo()));
        movie.setDirectors(directors);
        movie.setGenres(genres);
        movie.setActors(actors);
        movie.setDistributor(distributorOptional.get());
        movieRepository.save(movie);

        return new ResponseEntity(new ApiResponse("success",
                true, null), HttpStatus.OK);
    }

    @Override
    public HttpEntity deleteMovie(Integer id) {
        List<Movie> movieList = movieRepository.findAll();
        for (Movie movie : movieList) {
            if (movie.getId().equals(id)) {
                movieRepository.deleteById(id);
                return new ResponseEntity(new ApiResponse("success",
                        true, true), HttpStatus.OK);

            }
        }
        return new ResponseEntity(new ApiResponse("wrong",
                false, false), HttpStatus.NOT_FOUND);
    }

    public HttpEntity<?> editMovie(MovieDto movieDto, Integer id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (!optionalMovie.isPresent()) {
            return new ResponseEntity(new ApiResponse("wrong",
                    false, false), HttpStatus.NOT_FOUND);
        }
        Movie movie = optionalMovie.get();
        Optional<Distributor> distributorOptional = distributorRepository.findById(movieDto.getDistributorId());
        if (!distributorOptional.isPresent()) {
            return new ResponseEntity(new ApiResponse("wrong",
                    false, null), HttpStatus.BAD_REQUEST);
        }

        ArrayList<Director> directors = new ArrayList<>();
        for (Integer directorId : movieDto.getDirectorIds()) {
            if (!directorRepository.findById(directorId).isPresent()) {
                return new ResponseEntity(new ApiResponse("wrong",
                        false, null), HttpStatus.BAD_REQUEST);
            }
            directors.add(directorRepository.findById(directorId).get());
        }

        ArrayList<Genre> genres = new ArrayList<>();
        for (Integer genreId : movieDto.getGenreIds()) {
            if (!genreRepository.findById(genreId).isPresent()) {
                return new ResponseEntity(new ApiResponse("wrong",
                        false, null), HttpStatus.BAD_REQUEST);
            }
            genres.add(genreRepository.findById(genreId).get());
        }
        ArrayList<Actor> actors = new ArrayList<>();
        for (Integer actorId : movieDto.getGenreIds()) {
            if (!actorRepository.findById(actorId).isPresent()) {
                return new ResponseEntity(new ApiResponse("wrong",
                        false, null), HttpStatus.BAD_REQUEST);
            }
            actors.add(actorRepository.findById(actorId).get());
        }

        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setDurationInMinutes(movieDto.getDurationInMinutes());
        movie.setMinPrice(movieDto.getMinPrice());
        movie.setDistributorShareInPercent(movieDto.getDistributorShareInPercent());
        movie.setCoverImage(attachmentService.saveAttachment(movieDto.getCoverImage()));
        movie.setTrailerVideo(attachmentService.saveAttachment(movieDto.getTrailerVideo()));
        movie.setDirectors(directors);
        movie.setGenres(genres);
        movie.setActors(actors);
        movie.setDistributor(distributorOptional.get());
        movieRepository.save(movie);

        return new ResponseEntity(new ApiResponse("success",
                true, null), HttpStatus.OK);
    }
}
