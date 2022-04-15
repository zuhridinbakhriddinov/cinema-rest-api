package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcinemarest.entity.TransactionalHistory;

import java.util.List;

public interface TransactionalHistoryRepository extends JpaRepository<TransactionalHistory, Integer> {

 //   String findByTransactionalHistoryByUserId(Integer user_id);
 // List<TransactionalHistory> findByUserId(Integer userId);

    @Query(value = "select th.payment_intent\n" +
            "from transactional_history th\n" +
            "join transactional_histories_tickets tht on th.id = tht.transactional_history_id\n" +
            "where tht.ticket_id = :ticketId\n" +
            "and th.is_refunded=false",nativeQuery = true)
    String getPaymentIntentByTicketId(Integer ticketId);

TransactionalHistory findByPaymentIntent(String paymentIntent);
}
