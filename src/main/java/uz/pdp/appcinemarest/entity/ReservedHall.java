package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Zuhridin Bakhriddinov 3/17/2022 10:23 AM
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ReservedHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Afisha afisha;

    @OneToOne(cascade = CascadeType.ALL)
    private Hall hall;

    @OneToOne(cascade = CascadeType.ALL)
    private SessionDate startDate;

    @OneToOne(cascade = CascadeType.ALL)
    private SessionTime startTime;

    @OneToOne(cascade = CascadeType.ALL)
    private SessionTime endTime;


}
