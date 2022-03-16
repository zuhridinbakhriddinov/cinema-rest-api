package uz.pdp.appcinemarest.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcinemarest.entity.Attachment;
import uz.pdp.appcinemarest.entity.AttachmentContent;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.repository.AttachmentContentRepository;
import uz.pdp.appcinemarest.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

// Zuhridin Bakhriddinov 3/15/2022 12:23 PM
@RestController
@RequestMapping("/getFile")
public class AttachmentController {
    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @SneakyThrows
    @GetMapping("/{id}")
    public void getFile(@PathVariable Integer id, HttpServletResponse response){

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
            if (contentOptional.isPresent()) {
                AttachmentContent attachmentContent = contentOptional.get();
                response.setHeader("Content-Disposition","attachment; filename=\""+attachment.getName()+"\"");

                response.setContentType(attachment.getContentType());

                FileCopyUtils.copy(attachmentContent.getData(),response.getOutputStream());
            }

        }


    }

}
