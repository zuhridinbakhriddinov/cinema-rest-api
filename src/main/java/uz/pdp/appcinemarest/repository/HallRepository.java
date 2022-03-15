package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcinemarest.entity.Actor;
import uz.pdp.appcinemarest.entity.Hall;

// Zuhridin Bakhriddinov 3/15/2022 12:50 AM
public interface HallRepository extends JpaRepository<Hall,Integer> {
}
