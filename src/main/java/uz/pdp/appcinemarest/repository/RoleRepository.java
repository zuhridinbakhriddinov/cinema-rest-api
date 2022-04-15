package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcinemarest.entity.Hall;
import uz.pdp.appcinemarest.entity.Role;
import uz.pdp.appcinemarest.entity.enums.Role_enum;
import uz.pdp.appcinemarest.projection.CustomHall;
import uz.pdp.appcinemarest.projection.HallAndTimesProjectionForSession;

import java.util.List;

// Zuhridin Bakhriddinov 3/15/2022 12:50 AM
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByName(Role_enum name);
}
