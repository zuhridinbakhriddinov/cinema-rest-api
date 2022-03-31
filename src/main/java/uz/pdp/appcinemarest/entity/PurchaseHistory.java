package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

// Zuhridin Bakhriddinov 3/14/2022 7:53 PM
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
    * user id
    * ticket id
    * date
    * payId
    * */

    @ManyToOne
    private User user;

    @OneToOne
    private Ticket ticket;

    private LocalDateTime date;

    @OneToOne
    private PayType payType;

    private String chargeId;

    public PurchaseHistory(User user, Ticket ticket, LocalDateTime date, String chargeId) {
        this.user = user;
        this.ticket = ticket;
        this.date = date;
        this.chargeId = chargeId;
    }
}
