package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Zuhridin Bakhriddinov 3/14/2022 6:20 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Hall hall;

    private boolean isActive;





}
