package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcinemarest.entity.Permission;
import uz.pdp.appcinemarest.entity.Role;
import uz.pdp.appcinemarest.entity.enums.Role_enum;

import java.util.List;

// Zuhridin Bakhriddinov 3/15/2022 12:50 AM
public interface PermissionRepository extends JpaRepository<Permission,Integer> {
@Query(nativeQuery = true,value = "select p.name\n" +
        "from users u\n" +
        "join role r on r.id = u.role_id\n" +
        "join roles_permissions rp on r.id = rp.role_id\n" +
        "join permission p on p.id = rp.permission_id\n" +
        "where u.id = :userId")
    List<Permission> getPermissionList(Integer userId);
}
