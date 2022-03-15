package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcinemarest.entity.Actor;

// Zuhridin Bakhriddinov 3/15/2022 12:50 AM
public interface ActorRepository extends JpaRepository<Actor,Integer> {
}
