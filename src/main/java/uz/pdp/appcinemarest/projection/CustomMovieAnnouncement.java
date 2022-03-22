package uz.pdp.appcinemarest.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.pdp.appcinemarest.entity.MovieAnnouncement;

import java.util.List;

@Projection(types = MovieAnnouncement.class)
public interface CustomMovieAnnouncement {
 /*   Integer getId();
    Integer getMovieId();

    String getMovieTitle();

    Integer getMovieCoverImgId();

    Integer getStartedDateId();



    @Value("#{@hallRepository.getAllHallByMovieId(target.id)}")
    List<CustomHall> getHalls();*/



}
