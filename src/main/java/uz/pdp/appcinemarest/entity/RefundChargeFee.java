package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Zuhridin Bakhriddinov 3/14/2022 7:34 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RefundChargeFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int intervalInMinutes;
    private double percentage;

    
}
