package uz.pdp.appcinemarest.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.appcinemarest.entity.Hall;

@Projection(types = Hall.class)
public interface CustomHall {

    Integer getId();
    String getName();


}
