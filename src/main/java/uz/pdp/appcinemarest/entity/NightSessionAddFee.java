package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

// Zuhridin Bakhriddinov 3/14/2022 6:58 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class NightSessionAddFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double percentage;
    private Date date;

}
