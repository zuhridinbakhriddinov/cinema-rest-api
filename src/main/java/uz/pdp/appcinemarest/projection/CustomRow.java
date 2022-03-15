package uz.pdp.appcinemarest.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.appcinemarest.entity.Row;

@Projection(types = Row.class)
public interface CustomRow {

    int getId();

    Integer getNumber();

    String getHall();
}
