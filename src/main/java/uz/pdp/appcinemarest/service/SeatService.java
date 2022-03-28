package uz.pdp.appcinemarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.projection.AvailableSeatProjection;
import uz.pdp.appcinemarest.projection.MovieSessionProjection;
import uz.pdp.appcinemarest.projection.SeatsProjection;
import uz.pdp.appcinemarest.repository.SeatRepository;

import java.util.List;

// Zuhridin Bakhriddinov 3/24/2022 11:00 PM
@Service
public class SeatService {
    @Autowired
    SeatRepository seatRepository;

    public HttpEntity getAllMovieSessions(Integer movieSessionId) {

        if (movieSessionId != null) {
            List<AvailableSeatProjection> availableSeatForSession = seatRepository.getAvailableSeatsByMovieSessionId(movieSessionId);
            return new ResponseEntity(new ApiResponse("success",
                    true, availableSeatForSession), HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponse("Wrong",
                    false, null), HttpStatus.BAD_REQUEST);
        }


    }
}
