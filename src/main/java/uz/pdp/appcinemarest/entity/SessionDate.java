package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

// Zuhridin Bakhriddinov 3/14/2022 6:26 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SessionDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;
}
