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
public class MovieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private MovieAnnouncement movieAnnouncement;

    @ManyToOne(cascade = CascadeType.ALL)
    private Hall hall;

    @ManyToOne(cascade = CascadeType.ALL)
    private SessionDate startDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private SessionTime startTime;

    @ManyToOne(cascade = CascadeType.ALL)
    private SessionTime endTime;


    public MovieSession(MovieAnnouncement movieAnnouncement, Hall hall, SessionDate startDate, SessionTime startTime, SessionTime endTime) {
        this.movieAnnouncement = movieAnnouncement;
        this.hall = hall;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
