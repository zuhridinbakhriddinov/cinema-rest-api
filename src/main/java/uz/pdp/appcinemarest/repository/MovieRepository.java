package uz.pdp.appcinemarest.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcinemarest.entity.Movie;
import uz.pdp.appcinemarest.projection.CustomMovie;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

 /*  @Query(value = "select m.id,m.title,m.description,a.id as coverImage\n" +
           "from movie m\n" +
           "join attachment a on a.id = m.cover_image_id",nativeQuery = true)
    Page<CustomMovie> getAllMovie(Pageable pageable);*/


    @Query(value = "select m.id,m.title,m.description,a.id as coverImage\n" +
            "from movie m\n" +
            "join attachment a on a.id = m.cover_image_id",nativeQuery = true)
    Page<CustomMovie> findAllByPage(Pageable pageable, String search);

}
