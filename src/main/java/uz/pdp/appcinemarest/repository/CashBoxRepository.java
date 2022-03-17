package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcinemarest.entity.CashBox;
import uz.pdp.appcinemarest.projection.CustomCashBox;
import uz.pdp.appcinemarest.projection.CustomDirector;

@RepositoryRestResource(path = "director",collectionResourceRel = "list",excerptProjection = CustomCashBox.class)
public interface CashBoxRepository extends JpaRepository<CashBox,Integer> {
}
