package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcinemarest.entity.Attachment;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.repository.AttachmentContentRepository;
import uz.pdp.appcinemarest.repository.AttachmentRepository;

import java.util.Optional;

// Zuhridin Bakhriddinov 3/15/2022 12:23 PM
@RestController
@RequestMapping("/upload")
public class AttachmentController {
    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @GetMapping("/{id}")
    public HttpEntity<?> getFile(@PathVariable Integer id){
        Optional<Attachment> attachmentRepositoryById = attachmentRepository.findById(id);
        return attachmentRepositoryById.map(attachment -> new ResponseEntity(new ApiResponse("success",
                true, attachment), HttpStatus.OK)).orElseGet(() -> new ResponseEntity(new ApiResponse("wrong",
                false, null), HttpStatus.OK));

    }

}
