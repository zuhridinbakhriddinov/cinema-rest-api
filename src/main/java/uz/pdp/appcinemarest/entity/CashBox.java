package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appcinemarest.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Zuhridin Bakhriddinov 3/14/2022 6:56 PM
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class CashBox extends AbsEntity {

    private double balance;


}
