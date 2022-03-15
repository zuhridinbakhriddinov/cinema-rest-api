package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private Date date;

    @OneToOne
    private PayType payType;




}
