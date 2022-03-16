package uz.pdp.appcinemarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appcinemarest.entity.Actor;
import uz.pdp.appcinemarest.entity.Attachment;
import uz.pdp.appcinemarest.entity.AttachmentContent;
import uz.pdp.appcinemarest.payload.ActorDto;
import uz.pdp.appcinemarest.repository.AttachmentContentRepository;
import uz.pdp.appcinemarest.repository.AttachmentRepository;

import java.io.IOException;

// Zuhridin Bakhriddinov 3/16/2022 10:15 PM
@Service
public class AttachmentService {
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;


    public Attachment saveAttachment(MultipartFile file) {
        try {
            Attachment attachment = new Attachment();
            attachment.setName(file.getName());
            attachment.setContentType(file.getContentType());
            attachment.setSize(file.getSize());
            attachmentRepository.save(attachment);
            attachmentContentRepository.save(new AttachmentContent(attachment, file.getBytes()));
            return attachment;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
