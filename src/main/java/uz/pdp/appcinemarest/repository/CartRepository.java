package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcinemarest.entity.Cart;
import uz.pdp.appcinemarest.entity.User;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Cart findByUserId(Integer user_id);
}
