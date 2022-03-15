package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appcinemarest.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

// Zuhridin Bakhriddinov 3/14/2022 3:49 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Permission extends AbsEntity {

    @ManyToMany
    private List<Role> role;

}
