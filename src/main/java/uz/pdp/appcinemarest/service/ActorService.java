package uz.pdp.appcinemarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.appcinemarest.entity.Actor;
import uz.pdp.appcinemarest.entity.Attachment;
import uz.pdp.appcinemarest.entity.AttachmentContent;
import uz.pdp.appcinemarest.entity.Distributor;
import uz.pdp.appcinemarest.payload.ActorDto;
import uz.pdp.appcinemarest.payload.DistributorDto;
import uz.pdp.appcinemarest.repository.ActorRepository;
import uz.pdp.appcinemarest.repository.AttachmentContentRepository;
import uz.pdp.appcinemarest.repository.AttachmentRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

// Zuhridin Bakhriddinov 3/15/2022 8:44 AM
@Service
public class ActorService {
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    public Actor saveActor(MultipartFile file, ActorDto actorDto) {
        try {
            Attachment attachment = new Attachment();
            attachment.setName(file.getName());
            attachment.setContentType(file.getContentType());
            attachment.setSize(file.getSize());
            attachmentRepository.save(attachment);
            attachmentContentRepository.save(new AttachmentContent(attachment, file.getBytes()));
            Actor actor = new Actor();
            actor.setName(actorDto.getName());
            actor.setPhoto(attachment);
            return actorRepository.save(actor);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteActor(int id) {
        List<Actor> all = actorRepository.findAll();

        for (Actor actor : all) {
            if (!actor.getId().equals(id)) {
                return false;
            }

        }
        actorRepository.deleteById(id);
        return true;

    }


    public boolean editActor(int id, ActorDto actorDto, MultipartFile file) {
        Optional<Actor> actorOptional = actorRepository.findById(id);
        if (!actorOptional.isPresent()) {
            return false;
        }

        try {
            Actor actor1 = actorOptional.get();
            Attachment attachment1 = actor1.getPhoto();

            AttachmentContent attachmentContent1 = attachment1.getAttachmentContent();
            attachment1.setName(file.getName());
            attachment1.setContentType(file.getContentType());
            attachment1.setSize(file.getSize());
//            actorRepository.deleteById(actorDto.getPhotoId());

            attachmentContent1.setData(file.getBytes());
            attachmentContent1.setAttachment(attachment1);

           // attachmentContentRepository.save(new AttachmentContent(attachment1, file.getBytes()));
            Actor actor = actorOptional.get();
            actor1.setName(actorDto.getName());
            actor1.setPhoto(attachment1);
            actorRepository.save(actor1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Actor> getActorList() {
        return actorRepository.findAll();
    }

}
