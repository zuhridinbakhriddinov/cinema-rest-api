package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcinemarest.entity.PurchaseHistory;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Integer> {

 //   String findByPurchaseHistoryByUserId(Integer user_id);
  List<PurchaseHistory> findByUserId(Integer userId);
}
