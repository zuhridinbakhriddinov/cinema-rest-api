package uz.pdp.appcinemarest.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.List;

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
