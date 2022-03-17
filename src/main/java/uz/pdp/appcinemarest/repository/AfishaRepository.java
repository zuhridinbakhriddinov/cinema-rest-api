package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcinemarest.entity.Afisha;
import uz.pdp.appcinemarest.projection.CustomAfisha;

import java.util.List;

public interface AfishaRepository extends JpaRepository<Afisha, Integer> {


    @Query(nativeQuery = true, value = "select a.id             as id,\n" +
            "       m.id             as movieId,\n" +
            "       m.title          as movieTitle,\n" +
            "       m.cover_image_id as movieCoverImgId,\n" +
            "       sd.id            as startedDateId\n" +
            "from afisha a\n" +
            "         join movie m on m.id = a.movie_id\n" +
            "         join reserved_hall rh on a.id = rh.afisha_id\n" +
            "         join session_date sd on rh.start_date_id = sd.id\n" +
            "where sd.date > date(now())")
    List<CustomAfisha> getAllAfisha();


}
