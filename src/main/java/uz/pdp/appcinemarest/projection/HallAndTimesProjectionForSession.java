package uz.pdp.appcinemarest.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface HallAndTimesProjectionForSession {

    Integer getId();

    String getName();


/*    Integer getMovieAnnouncementId();

    Integer getStartDateId();*/

    @Value("#{@sessionTimeRepository.getTimesByHallIdAndAnnouncementIdAndStartDateId(target.id, target.movieAnnouncementId, target.startDateId)}")
    List<SessionTimeProjection> getTimes();


}
