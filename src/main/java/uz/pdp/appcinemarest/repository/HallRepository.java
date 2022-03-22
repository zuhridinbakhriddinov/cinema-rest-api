package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcinemarest.entity.Hall;
import uz.pdp.appcinemarest.projection.CustomHall;
import uz.pdp.appcinemarest.projection.HallAndTimesProjectionForSession;

import java.util.List;
import java.util.UUID;

// Zuhridin Bakhriddinov 3/15/2022 12:50 AM
@RepositoryRestResource(path = "hall",collectionResourceRel = "list",excerptProjection = CustomHall.class)
public interface HallRepository extends JpaRepository<Hall,Integer> {




    @Query(nativeQuery = true, value = "select distinct\n" +
            "             h.id  as id,\n" +
            "                   h.name,\n" +
            "             rh.start_date_id  as startDateId,\n" +
            "             movie_announcement_id  as movieAnnouncementId\n" +
            "            from hall h \n" +
            "                     join movie_session rh on h.id = rh.hall_id\n" +
            "            where rh.start_date_id = :startDateId\n" +
            "              and movie_announcement_id = :movieAnnouncementId")
    List<HallAndTimesProjectionForSession> getHallsAndTimesByMovieAnnouncementIdAndStartDateId(int movieAnnouncementId, int startDateId);



}
