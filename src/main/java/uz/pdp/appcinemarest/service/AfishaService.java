package uz.pdp.appcinemarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.projection.CustomAfisha;
import uz.pdp.appcinemarest.repository.AfishaRepository;

import java.util.List;

// Zuhridin Bakhriddinov 3/17/2022 10:36 AM
@Service
public class AfishaService {

@Autowired
AfishaRepository afishaRepository;


    public HttpEntity getAllAfisha(int page, int size, String search, String sort, boolean b) {
        List<CustomAfisha> allAfisha = afishaRepository.getAllAfisha();
        return new ResponseEntity(new ApiResponse("success",
                true, allAfisha), HttpStatus.OK);

    }
}
