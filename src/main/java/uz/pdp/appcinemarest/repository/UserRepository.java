package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.appcinemarest.entity.User;
import uz.pdp.appcinemarest.projection.AdminDashboardProjection;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

/*    @Query(nativeQuery = true,value = "")
    List<AdminDashboardProjection> getStatistic(String date);*/

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndEmailCode(String email, String emailCode);

    Optional<UserDetails> findByEmail(String email);




}
