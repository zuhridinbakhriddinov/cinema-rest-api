package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appcinemarest.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

// Zuhridin Bakhriddinov 3/14/2022 5:09 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Actor extends AbsEntity {

    @OneToOne
    private Attachment photo;


}
