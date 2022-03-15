package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcinemarest.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {


}
