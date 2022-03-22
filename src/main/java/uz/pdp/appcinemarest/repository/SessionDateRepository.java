package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcinemarest.entity.SessionDate;
import uz.pdp.appcinemarest.projection.CustomSessionDate;

@RepositoryRestResource(path = "sessionDate",collectionResourceRel = "list",excerptProjection = CustomSessionDate.class)
public interface SessionDateRepository extends JpaRepository<SessionDate,Integer> {
}
