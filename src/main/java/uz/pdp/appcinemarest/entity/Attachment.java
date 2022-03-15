package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appcinemarest.entity.template.AbsEntity;

import javax.persistence.Entity;

// Zuhridin Bakhriddinov 3/14/2022 5:04 PM
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attachment extends AbsEntity {

    private String contentType;

    private Long size;

}
