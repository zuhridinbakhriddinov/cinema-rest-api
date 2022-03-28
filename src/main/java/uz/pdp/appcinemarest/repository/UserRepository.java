package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcinemarest.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {



}
