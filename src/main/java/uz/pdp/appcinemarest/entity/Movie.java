package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

// Zuhridin Bakhriddinov 3/14/2022 5:46 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private int durationInMinutes;
    @OneToOne
    private Attachment coverImage;

    @OneToOne
    private Attachment trailerVideo;

    @ManyToMany
    private List<Director> directors;

    @ManyToMany
    private List<Genre> genres;

    private double minPrice;

    @OneToOne
    private Distributor distributor;

    @ManyToMany
    private List<Actor> actors;

    private double distributorShareInPercent;









}
