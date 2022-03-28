package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.projection.AvailableSeatProjection;
import uz.pdp.appcinemarest.projection.RowsProjection;
import uz.pdp.appcinemarest.repository.RowRepository;
import uz.pdp.appcinemarest.repository.SeatRepository;
import uz.pdp.appcinemarest.service.SeatService;
import uz.pdp.appcinemarest.utill.Constants;

import java.util.List;

// Zuhridin Bakhriddinov 3/24/2022 11:08 PM
@RestController
@RequestMapping("/seat")
public class SeatController {
    @Autowired
    SeatService seatService;


    @GetMapping("showAvailableSeat/{movieSessionId}")
    public HttpEntity getAllMovieSessions(@PathVariable Integer movieSessionId) {
       return seatService.getAllMovieSessions(movieSessionId);
    }
}
