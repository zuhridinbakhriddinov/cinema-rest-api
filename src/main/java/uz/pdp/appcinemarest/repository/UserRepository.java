package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcinemarest.entity.User;
import uz.pdp.appcinemarest.projection.AdminDashboardProjection;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

/*    @Query(nativeQuery = true,value = "")
    List<AdminDashboardProjection> getStatistic(String date);*/

}
