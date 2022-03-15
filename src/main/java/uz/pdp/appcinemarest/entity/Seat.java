package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Zuhridin Bakhriddinov 3/14/2022 4:14 PM
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int number;

    @ManyToOne
    private Row row;

    @OneToOne
    private PriceCategory priceCategory;

}
