package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcinemarest.entity.Afisha;
import uz.pdp.appcinemarest.projection.CustomAfisha;

import java.util.List;

public interface AfishaRepository extends JpaRepository<Afisha, Integer> {


    @Query(nativeQuery = true, value = "select a.id as id,\n" +
            "       m.id as movieId,\n" +
            "       m.title as movieTitle,\n" +
            "       m.cover_image_id as movieCoverImgId,\n" +
            "        sd.date as startedDate\n" +
            "from afisha a\n" +
            "join movie m on m.id = a.movie_id\n" +
            "join session_date sd on a.id = sd.afisha_id\n" +
            "where sd.date>date(now())")
    List<CustomAfisha> getAllAfisha();


}
