package uz.pdp.appcinemarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appcinemarest.entity.*;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.payload.MovieSessionDto;
import uz.pdp.appcinemarest.projection.MovieSessionProjection;
import uz.pdp.appcinemarest.repository.*;

import java.util.List;
import java.util.Optional;

// Zuhridin Bakhriddinov 3/18/2022 11:01 AM
@Service
public class MovieSessionService {

    @Autowired
    MovieSessionRepository movieSessionRepository;

    @Autowired
    HallRepository hallRepository;
    @Autowired
    MovieAnnouncementRepository movieAnnouncementRepository;

    @Autowired
    SessionDateRepository sessionDateRepository;
    @Autowired
    SessionTimeRepository sessionTimeRepository;


    public HttpEntity getAllMovieSessions(int page, int size, String search) {
        Pageable pageable = PageRequest.of(
                page - 1,
                size
        );
        Page<MovieSessionProjection> all = movieSessionRepository.findAllSessionsByPage(
                pageable,
                search);

        return ResponseEntity.ok(new ApiResponse("success", true, all));

    }


    public HttpEntity addMovieSession(MovieSessionDto movieSessionDto) {

        MovieAnnouncement movieAnnouncement = movieAnnouncementRepository.findById(movieSessionDto.getMovieAnnouncementId()).orElseThrow(NullPointerException::new);
        Hall hall = hallRepository.findById(movieSessionDto.getHallId()).orElseThrow(RuntimeException::new);
        SessionDate startDate = sessionDateRepository.findById(movieSessionDto.getStartDateId()).orElseThrow(NullPointerException::new);
        SessionTime startTime = sessionTimeRepository.findById(movieSessionDto.getStartTimeId()).orElseThrow(ArithmeticException::new);
        SessionTime endTime = sessionTimeRepository.findById(movieSessionDto.getEndTimeId()).orElseThrow(ArithmeticException::new);
        MovieSession movieSession = new MovieSession(movieAnnouncement, hall, startDate, startTime, endTime);
         movieSessionRepository.save(movieSession);
        return new ResponseEntity(new ApiResponse("Success",
                true, true), HttpStatus.OK);


    }


    public HttpEntity deleteMovieSession(int id) {
        List<MovieSession> list = movieSessionRepository.findAll();
        for (MovieSession movieSession : list) {
            if (movieSession.getId()!=id)
                return new ResponseEntity(new ApiResponse("Wrong", false, null), HttpStatus.BAD_REQUEST);
        }
        movieSessionRepository.deleteById(id);
        return new ResponseEntity(new ApiResponse("Success", true, true), HttpStatus.OK);

    }


    public HttpEntity updateMovieSession(MovieSessionDto movieSessionDto, int id) {
        MovieAnnouncement movieAnnouncement = movieAnnouncementRepository.findById(movieSessionDto.getMovieAnnouncementId()).orElseThrow(NullPointerException::new);
        Hall hall = hallRepository.findById(movieSessionDto.getHallId()).orElseThrow(RuntimeException::new);
        SessionDate startDate = sessionDateRepository.findById(movieSessionDto.getStartDateId()).orElseThrow(NullPointerException::new);
        SessionTime startTime = sessionTimeRepository.findById(movieSessionDto.getStartTimeId()).orElseThrow(ArithmeticException::new);
        SessionTime endTime = sessionTimeRepository.findById(movieSessionDto.getEndTimeId()).orElseThrow(ArithmeticException::new);
        Optional<MovieSession> optionalMovieSession = movieSessionRepository.findById(id);
        if (!optionalMovieSession.isPresent()) {
            return new ResponseEntity(new ApiResponse("Wrong",
                    false, null), HttpStatus.BAD_REQUEST);
        }
        MovieSession movieSession = optionalMovieSession.get();
        movieSession.setMovieAnnouncement(movieAnnouncement);
        movieSession.setHall(hall);
        movieSession.setStartDate(startDate);
        movieSession.setStartTime(startTime);
        movieSession.setEndTime(endTime);
        movieSessionRepository.save(movieSession);
        return new ResponseEntity(new ApiResponse("Success",
                true, true), HttpStatus.OK);
    }
}
