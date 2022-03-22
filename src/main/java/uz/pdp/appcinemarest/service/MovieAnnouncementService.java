package uz.pdp.appcinemarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appcinemarest.entity.MovieAnnouncement;
import uz.pdp.appcinemarest.entity.Movie;
import uz.pdp.appcinemarest.payload.MovieAnnouncementDto;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.projection.CustomMovieAnnouncement;
import uz.pdp.appcinemarest.repository.MovieAnnouncementRepository;
import uz.pdp.appcinemarest.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

// Zuhridin Bakhriddinov 3/17/2022 10:36 AM
@Service
public class MovieAnnouncementService {

    @Autowired
    MovieAnnouncementRepository movieAnnouncementRepository;
    @Autowired
    MovieRepository movieRepository;


   /* public HttpEntity getAllAfisha(int page, int size, String search, String sort, boolean b) {

        List<CustomMovieAnnouncement> allAfisha = movieAnnouncementRepository.getAllAfisha();
        return new ResponseEntity(new ApiResponse("success",
                true, allAfisha), HttpStatus.OK);

    }*/

    public HttpEntity<?> saveAfisha(MovieAnnouncementDto movieAnnouncementDto) {

        MovieAnnouncement movieAnnouncement = new MovieAnnouncement();
        Optional<Movie> movieOptional = movieRepository.findById(movieAnnouncementDto.getMovieId());
        if (!movieOptional.isPresent()) {
            return new ResponseEntity<>(new ApiResponse("wrong", false, null), HttpStatus.CONFLICT);
        }

        movieAnnouncement.setMovie(movieOptional.get());
        movieAnnouncement.setActive(movieAnnouncementDto.getIsActive());
        MovieAnnouncement save = movieAnnouncementRepository.save(movieAnnouncement);
        return new ResponseEntity<>(new ApiResponse("success", true, save), HttpStatus.OK);

    }

    public HttpEntity<?> deleteAfisha(int id) {
        List<MovieAnnouncement> movieAnnouncementList = movieAnnouncementRepository.findAll();
        for (MovieAnnouncement movieAnnouncement : movieAnnouncementList) {
            if (!movieAnnouncement.getId().equals(id)) {
                return new ResponseEntity<>(new ApiResponse("wrong", false, null), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(new ApiResponse("success", true, true), HttpStatus.OK);

    }

    public HttpEntity<?> updateAfisha(int id, MovieAnnouncementDto movieAnnouncementDto) {
        Optional<MovieAnnouncement> optionalAfisha = movieAnnouncementRepository.findById(id);
        if (!optionalAfisha.isPresent()) {
            return new ResponseEntity<>(new ApiResponse("wrong", false, null), HttpStatus.NOT_FOUND);

        }
return null;
    }
}
