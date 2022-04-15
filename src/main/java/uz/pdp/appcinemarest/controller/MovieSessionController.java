package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcinemarest.payload.MovieSessionDto;
import uz.pdp.appcinemarest.service.MovieSessionService;
import uz.pdp.appcinemarest.utill.Constants;

// Zuhridin Bakhriddinov 3/18/2022 11:00 AM
@RestController
@RequestMapping("/movie-session")
public class MovieSessionController {


    @Autowired
    MovieSessionService movieSessionService;

    @GetMapping
    public HttpEntity getAllMovieSessions(
            @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "search", defaultValue = "") String search
    ) {
        return movieSessionService.getAllMovieSessions(
                page,
                size,
                search
        );
    }

    @PostMapping
    public HttpEntity addMovieSession(@RequestBody MovieSessionDto movieSessionDto) {
       return movieSessionService.addMovieSession(movieSessionDto);

    }
    @PutMapping("/{id}")
    public HttpEntity updateMovieSession(@RequestBody MovieSessionDto movieSessionDto, @PathVariable int id) {
       return movieSessionService.updateMovieSession(movieSessionDto,id);

    }

    @DeleteMapping("/{id}")
    public HttpEntity deleteActor(@PathVariable int id) {
    return movieSessionService.deleteMovieSession(id);

    }



}
