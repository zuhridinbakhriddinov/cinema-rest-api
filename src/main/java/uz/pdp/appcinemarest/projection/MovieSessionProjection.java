package uz.pdp.appcinemarest.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uz.pdp.appcinemarest.entity.MovieAnnouncement;
import uz.pdp.appcinemarest.entity.MovieSession;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MovieSessionProjection {
    Integer getMovieAnnouncementId(); // afisha id

    Integer getMovieId();

    String getMovieTitle();

    Integer getMovieCoverImgId();

    Integer getStartDateId();

    LocalDate getStartDate();

    @Value("#{@hallRepository.getHallsAndTimesByMovieAnnouncementIdAndStartDateId(target.movieAnnouncementId, target.startDateId)}")
    List<HallAndTimesProjectionForSession> getHalls();

}
