package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcinemarest.entity.Actor;
import uz.pdp.appcinemarest.entity.Hall;
import uz.pdp.appcinemarest.projection.CustomAfisha;
import uz.pdp.appcinemarest.projection.CustomGenre;
import uz.pdp.appcinemarest.projection.CustomHall;

import java.util.List;

// Zuhridin Bakhriddinov 3/15/2022 12:50 AM
@RepositoryRestResource(path = "hall",collectionResourceRel = "list",excerptProjection = CustomHall.class)
public interface HallRepository extends JpaRepository<Hall,Integer> {



    @Query(nativeQuery = true, value = "select h.id,h.name\n" +
            "from afisha\n" +
            "join hall h on h.id = afisha.hall_id\n" +
            "where movie_id = :movieId")
   List<CustomHall> getAllHallByMovieId(Integer movieId);


}
