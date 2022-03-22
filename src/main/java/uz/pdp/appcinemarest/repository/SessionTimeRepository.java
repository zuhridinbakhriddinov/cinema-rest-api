package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcinemarest.entity.SessionDate;
import uz.pdp.appcinemarest.entity.SessionTime;
import uz.pdp.appcinemarest.projection.CustomSessionDate;
import uz.pdp.appcinemarest.projection.CustomSessionTime;
import uz.pdp.appcinemarest.projection.SessionTimeProjection;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(path = "sessionTime",collectionResourceRel = "list",excerptProjection = CustomSessionTime.class)
public interface SessionTimeRepository extends JpaRepository<SessionTime,Integer> {
    @Query(value = "select distinct\n" +
            "            st.id  as id,\n" +
            "            ms.id  as sessionId,\n" +
            "                   time\n" +
            "            from session_time st\n" +
            "                     join movie_session ms on st.id = ms.start_time_id\n" +
            "            where ms.hall_id = :hallId\n" +
            "              and movie_announcement_id = :movieAnnouncementId\n" +
            "            and ms.start_date_id = :startDateId", nativeQuery = true)
    List<SessionTimeProjection> getTimesByHallIdAndAnnouncementIdAndStartDateId(Integer hallId, Integer movieAnnouncementId, Integer startDateId);



}
