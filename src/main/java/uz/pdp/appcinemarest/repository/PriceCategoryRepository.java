package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcinemarest.entity.Director;
import uz.pdp.appcinemarest.entity.PriceCategory;
import uz.pdp.appcinemarest.projection.CustomDirector;
import uz.pdp.appcinemarest.projection.CustomPriceCategory;

@RepositoryRestResource(path = "director",collectionResourceRel = "list",excerptProjection = CustomPriceCategory.class)
public interface PriceCategoryRepository extends JpaRepository<PriceCategory,Integer> {

}
