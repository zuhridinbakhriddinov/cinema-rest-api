package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Zuhridin Bakhriddinov 3/15/2022 11:43 AM
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    MovieSession movieSession;

    @ManyToOne(cascade = CascadeType.ALL)
    Hall hall;

}
