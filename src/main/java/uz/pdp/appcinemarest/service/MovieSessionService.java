package uz.pdp.appcinemarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.projection.MovieSessionProjection;
import uz.pdp.appcinemarest.repository.MovieSessionRepository;

// Zuhridin Bakhriddinov 3/18/2022 11:01 AM
@Service
public class MovieSessionService {

    @Autowired
    MovieSessionRepository movieSessionRepository;


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


}
