package uz.pdp.appcinemarest.projection;

import org.springframework.data.rest.core.config.Projection;
import uz.pdp.appcinemarest.entity.Attachment;
import uz.pdp.appcinemarest.entity.Hall;
import uz.pdp.appcinemarest.entity.Movie;

@Projection(types = Movie.class)
public interface CustomMovie {
    Integer getId();
    String getTitle();
    String getDescription();
    Integer getCoverImage();

}
