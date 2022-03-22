package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcinemarest.payload.MovieAnnouncementDto;
import uz.pdp.appcinemarest.service.MovieAnnouncementService;
import uz.pdp.appcinemarest.utill.Constants;

// Zuhridin Bakhriddinov 3/17/2022 10:35 AM
@RequestMapping("/afisha")
@RestController
public class MovieAnnouncementController {
    @Autowired
    MovieAnnouncementService movieAnnouncementService;


 /*   @GetMapping
    public HttpEntity getAllAfisha(
            @RequestParam(name = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "sort", defaultValue = "start_date") String sort
    ) {
        return movieAnnouncementService.getAllAfisha(page, size, search, sort, true);
    }*/

    @PostMapping
    public HttpEntity<?> addAfisha(@RequestBody MovieAnnouncementDto movieAnnouncementDto){

        return movieAnnouncementService.saveAfisha(movieAnnouncementDto);

    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteAfisha(@PathVariable int id){

        return movieAnnouncementService.deleteAfisha(id);

    }

    @PutMapping("/{id}")
    public HttpEntity<?> updateAfisha(@PathVariable int id, MovieAnnouncementDto movieAnnouncementDto){

        return movieAnnouncementService.updateAfisha(id, movieAnnouncementDto);

    }

}
