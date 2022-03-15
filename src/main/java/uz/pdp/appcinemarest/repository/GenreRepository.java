package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcinemarest.entity.Genre;
import uz.pdp.appcinemarest.projection.CustomGenre;

@RepositoryRestResource(path = "genre",collectionResourceRel = "list",excerptProjection = CustomGenre.class)
public interface GenreRepository extends JpaRepository<Genre,Integer> {

}
