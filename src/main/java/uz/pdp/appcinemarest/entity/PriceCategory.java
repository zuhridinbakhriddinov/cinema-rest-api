package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appcinemarest.entity.template.AbsEntity;

import javax.persistence.Entity;

// Zuhridin Bakhriddinov 3/14/2022 4:14 PM
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceCategory extends AbsEntity {

    private Double additionalFeeInPercent;

    private String color;

}
