package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.projection.CustomRow;
import uz.pdp.appcinemarest.service.RowService;

import java.util.List;

// Zuhridin Bakhriddinov 3/15/2022 2:47 PM
@RestController
@RequestMapping("/row")
public class RowController {
    @Autowired
    RowService rowService;
    @GetMapping("/{hallId}")
    public HttpEntity<?> getRowsByHallId(@PathVariable int hallId){
        List<CustomRow> row = rowService.getRow(hallId);
        return new ResponseEntity(new ApiResponse("success",
                true, row), HttpStatus.OK);

    }


}
