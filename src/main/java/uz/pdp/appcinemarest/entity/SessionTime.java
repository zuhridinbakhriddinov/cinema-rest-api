package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

// Zuhridin Bakhriddinov 3/14/2022 6:30 PM
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class SessionTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Time time;




}
