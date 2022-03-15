package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appcinemarest.entity.template.AbsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

// Zuhridin Bakhriddinov 3/14/2022 4:14 PM
@Entity

@NoArgsConstructor
@Data
public class Hall extends AbsEntity {

    private double vipAdditionalFeeInPercent;

    @OneToMany(mappedBy = "hall",cascade = CascadeType.ALL)
    private List<Row> rowList;

    public Hall(double vipAdditionalFeeInPercent, List<Row> rowList) {
        this.vipAdditionalFeeInPercent = vipAdditionalFeeInPercent;
        this.rowList = rowList;
    }
}
