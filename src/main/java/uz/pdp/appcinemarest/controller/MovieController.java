package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcinemarest.entity.Movie;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.payload.MovieDto;
import uz.pdp.appcinemarest.projection.CustomMovie;
import uz.pdp.appcinemarest.repository.MovieRepository;
import uz.pdp.appcinemarest.service.MovieService;
import uz.pdp.appcinemarest.utill.Constants;

import java.util.Optional;

// Zuhridin Bakhriddinov 3/15/2022 4:10 PM
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @Autowired
    MovieRepository movieRepository;

    /*
     *------------------- Simple pagination-----------------------
     * */

  /*  @GetMapping
    public Page<CustomMovie> getAllMovie(@RequestParam int page) {

        Pageable pageable = PageRequest.of(page, Constants.DEFAULT_PAGE_SIZE);
        return movieRepository.getAllMovie(pageable);
    }*/

    /*------------------ Advanced pagination------------------ */
    @CrossOrigin
    @GetMapping
    public HttpEntity getAllMovies(
            @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "title") String sort
    ) {
        return movieService.getAllMovies(page, size, search, sort, true);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getMovieById(@PathVariable int id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (!optionalMovie.isPresent()) {
            return new ResponseEntity(new ApiResponse("wrong",
                    true, null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(new ApiResponse("success",
                true, optionalMovie.get()), HttpStatus.OK);
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity saveMovie(
            MovieDto movie
    ) {
        System.out.println(movie);
        movieService.saveMovie(movie);
        return new ResponseEntity("save", HttpStatus.OK);
    }

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/{id}")
    public ResponseEntity editMovie(
            MovieDto movie,
            @PathVariable Integer id) {
        System.out.println(movie);
        movieService.editMovie(movie, id);
        return new ResponseEntity("save", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteMovie(@PathVariable int id) {
        return movieService.deleteMovie(id);

    }


}
