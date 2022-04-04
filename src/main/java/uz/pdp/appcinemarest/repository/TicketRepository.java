package uz.pdp.appcinemarest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appcinemarest.entity.Ticket;
import uz.pdp.appcinemarest.entity.enums.TicketStatus;
import uz.pdp.appcinemarest.projection.CustomTicketForCart;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    boolean existsBySeatId(Integer seat_id);

    @Query(nativeQuery = true,value = "select distinct m.min_price + (m.min_price * h.vip_additional_fee_in_percent / 100) +\n" +
            "                (pc.additional_fee_in_percent * m.min_price / 100) + (\n" +
            "                            (select n.percentage\n" +
            "                             from night_session_add_fee n\n" +
            "                             where date = (select st.time\n" +
            "                                           from movie_session\n" +
            "                                                    join session_time st on st.id = movie_session.start_time_id\n" +
            "                                           where movie_session.id = :movieSessionId\n" +
            "                             )\n" +
            "                            ) * m.min_price / 100)\n" +
            "from movie_session ms\n" +
            "         join movie_announcement ma on ms.movie_announcement_id = ma.id\n" +
            "         join movie m on m.id = ma.movie_id\n" +
            "         join hall h on ms.hall_id = h.id\n" +
            "         join row r on h.id = r.hall_id\n" +
            "         join seat s on r.id = s.row_id\n" +
            "         join price_category pc on s.price_category_id = pc.id\n" +
            "where ms.id = :movieSessionId\n" +
            "  and s.id = :seatId")
    Double getTicketPriceByMovieSessionIdAndSeatId(Integer movieSessionId,Integer seatId);


    @Query(nativeQuery = true,value = "select t.id as ticketId,t.ticket_status as ticketStatus\n" +
            "from ticket t\n" +
            "where t.id = :ticketId")
    CustomTicketForCart getTicketByIdForCart(Integer ticketId);


    List<Ticket> findAllByUserIdAndTicketStatus(Integer user_id, TicketStatus ticketStatus);


    @Query(nativeQuery = true,value = "select t.id          as ticketId,\n" +
            "       ticket_status as ticketStatus,\n" +
            "       m.title       as movieTitle,\n" +
            "       t.price       as price\n" +
            "from ticket t\n" +
            "         join movie_session ms on t.movie_session_id = ms.id\n" +
            "         join movie_announcement ma on ms.movie_announcement_id = ma.id\n" +
            "         join movie m on ma.movie_id = m.id\n" +
            "where t.user_id = :userId and t.ticket_status='NEW'")
    List<CustomTicketForCart> getTicketByUserId(Integer userId);
}
