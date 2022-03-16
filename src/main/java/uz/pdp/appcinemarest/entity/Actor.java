package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appcinemarest.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.List;

// Zuhridin Bakhriddinov 3/14/2022 5:09 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Actor extends AbsEntity {

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment photo;

    @ManyToMany(mappedBy = "actors", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Movie> movie;


}
