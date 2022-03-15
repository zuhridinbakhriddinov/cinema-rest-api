package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appcinemarest.entity.Actor;
import uz.pdp.appcinemarest.entity.Attachment;
import uz.pdp.appcinemarest.entity.AttachmentContent;
import uz.pdp.appcinemarest.entity.Distributor;
import uz.pdp.appcinemarest.payload.ActorDto;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.payload.DistributorDto;
import uz.pdp.appcinemarest.repository.ActorRepository;
import uz.pdp.appcinemarest.repository.AttachmentContentRepository;
import uz.pdp.appcinemarest.repository.AttachmentRepository;
import uz.pdp.appcinemarest.service.ActorService;

import java.io.IOException;
import java.util.Optional;

// Zuhridin Bakhriddinov 3/15/2022 12:43 AM
@RequestMapping("/actor")
@RestController
public class ActorController {

    @Autowired
    ActorService actorService;
    @PostMapping
    public HttpEntity<?> saveActor(@RequestPart("file") MultipartFile file,
                                   @RequestPart("json") ActorDto actorDto) {

        Actor actor = actorService.saveActor(file, actorDto);
        if (actor!=null){
            return new ResponseEntity(new ApiResponse("success",
                    true, actor ), HttpStatus.OK);
        }else
            return new ResponseEntity(new ApiResponse("Wrong",
                    false, null ), HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public HttpEntity deleteActor(@PathVariable int id) {
        boolean b = actorService.deleteActor(id);
        if (b)
            return new ResponseEntity("deleted", HttpStatus.OK);
        else
            return new ResponseEntity("not deleted", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public HttpEntity editActor(@PathVariable int id,@RequestPart("file") MultipartFile file,
                                @RequestPart("json") ActorDto actorDto){
        boolean b = actorService.editActor(id, actorDto,file);

        if (b) {
            return new ResponseEntity(new ApiResponse("success",
                    true, true), HttpStatus.OK);
        }
        else  return new ResponseEntity(new ApiResponse("wrong",
                false, false), HttpStatus.BAD_REQUEST);

    }






}
