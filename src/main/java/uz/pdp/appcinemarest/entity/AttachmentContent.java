package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.appcinemarest.entity.template.AbsEntity;

import javax.persistence.*;

// Zuhridin Bakhriddinov 3/14/2022 5:04 PM
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentContent  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = CascadeType.ALL)
    private Attachment attachment;

    private byte[] data;

    public AttachmentContent(Attachment attachment, byte[] data) {
        this.attachment = attachment;
        this.data = data;
    }
}
