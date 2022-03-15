package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcinemarest.entity.Director;
import uz.pdp.appcinemarest.projection.CustomDirector;

@RepositoryRestResource(path = "director",collectionResourceRel = "list",excerptProjection = CustomDirector.class)
public interface DirectorRepository extends JpaRepository<Director,Integer> {

}
