package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appcinemarest.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.List;

// Zuhridin Bakhriddinov 3/14/2022 4:14 PM
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Row  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int number;

    @ManyToOne
    private Hall hall;

    @OneToMany(mappedBy = "row",cascade = CascadeType.ALL)
    private List<Seat> seats;

    public Row(int number, Hall hall) {
        this.number = number;
        this.hall = hall;
    }
}
