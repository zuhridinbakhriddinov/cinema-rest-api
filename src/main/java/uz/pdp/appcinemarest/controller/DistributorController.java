package uz.pdp.appcinemarest.controller;

// Zuhridin Bakhriddinov 3/14/2022 8:13 PM

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcinemarest.entity.Distributor;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.payload.DistributorDto;
import uz.pdp.appcinemarest.repository.DistributorRepository;
import uz.pdp.appcinemarest.service.DistributorService;

import java.util.List;

@RequestMapping("/distributor")
@RestController
public class DistributorController {

    @Autowired
    DistributorService distributorService;


    @PostMapping
    public HttpEntity<?> registerUser(@RequestBody DistributorDto distributorDto) {

        Distributor distributor = distributorService.addDistributor(distributorDto);
        if (distributor == null)
            return new ResponseEntity(new ApiResponse("wrong",
                    false, null), HttpStatus.BAD_REQUEST);
        return new ResponseEntity(new ApiResponse("success",
                true, distributor), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public HttpEntity deleteDistributor(@PathVariable int id) {
        boolean distributor = distributorService.deleteDistributor(id);
        if (distributor)
            return new ResponseEntity("deleted", HttpStatus.OK);
        else
            return new ResponseEntity("not deleted", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public HttpEntity getAllGroups() {
        List<Distributor> distributorList = distributorService.getDistributorList();
        return new ResponseEntity(new ApiResponse("success",
                true, distributorList), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public HttpEntity editGroup(@PathVariable int id,@RequestBody DistributorDto distributorDto){
        boolean b = distributorService.editDistributor(id, distributorDto);
        if (b) {
            return new ResponseEntity(new ApiResponse("success",
                    true, true), HttpStatus.OK);
        }
        else  return new ResponseEntity(new ApiResponse("wrong",
                false, false), HttpStatus.BAD_REQUEST);

    }


}
