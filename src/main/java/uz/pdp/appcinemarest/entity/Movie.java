package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OneToOne(cascade = CascadeType.ALL)
    private Attachment coverImage;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment trailerVideo;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Director> directors;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Genre> genres;

    private double minPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private Distributor distributor;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Actor> actors;

    private double distributorShareInPercent;









}
