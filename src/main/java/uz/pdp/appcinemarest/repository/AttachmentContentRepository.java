package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcinemarest.entity.Attachment;
import uz.pdp.appcinemarest.entity.AttachmentContent;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent,Integer> {
    Optional<AttachmentContent> findByAttachmentId(Integer attachment_id);


}
