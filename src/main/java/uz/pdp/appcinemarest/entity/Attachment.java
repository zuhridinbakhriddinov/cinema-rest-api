package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appcinemarest.entity.template.AbsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

// Zuhridin Bakhriddinov 3/14/2022 5:04 PM
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attachment extends AbsEntity {

    private String contentType;

    private Long size;

    @OneToOne(mappedBy = "attachment",cascade = CascadeType.ALL)
    private AttachmentContent attachmentContent;

    public Attachment( String name, String contentType, Long size) {
        super( name);
        this.contentType = contentType;
        this.size = size;
    }
}
