package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcinemarest.entity.Ticket;
import uz.pdp.appcinemarest.projection.CustomTicketForCart;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    boolean existsBySeatId(Integer seat_id);

    @Query(nativeQuery = true,value = "select distinct m.min_price+(m.min_price*h.vip_additional_fee_in_percent/100)+(pc.additional_fee_in_percent*m.min_price/100)\n" +
            "from movie_session ms\n" +
            "join movie_announcement ma on ms.movie_announcement_id = ma.id\n" +
            "join movie m on m.id = ma.movie_id\n" +
            "join hall h on ms.hall_id = h.id\n" +
            "join row r on h.id = r.hall_id\n" +
            "join seat s on r.id = s.row_id\n" +
            "join price_category pc on s.price_category_id = pc.id\n" +
            "where ms.id = :movieSessionId\n" +
            "and s.id = :seatId")
    Double getTicketPriceByMovieSessionIdAndSeatId(Integer movieSessionId,Integer seatId);


    @Query(nativeQuery = true,value = "select t.id as ticketId,t.ticket_status as ticketStatus\n" +
            "from ticket t\n" +
            "where t.id = :ticketId")
    CustomTicketForCart getTicketByIdForCart(Integer ticketId);
}
