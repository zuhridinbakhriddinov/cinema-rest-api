package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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






}
