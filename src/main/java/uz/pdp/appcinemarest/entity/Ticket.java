package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appcinemarest.entity.enums.TicketStatus;

import javax.persistence.*;

// Zuhridin Bakhriddinov 3/14/2022 7:44 PM
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Afisha afisha;

    @OneToOne
    private Seat seat;

    @OneToOne
    private Attachment qrCode;

    private double price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    @ManyToOne
    private Cart cart;


}
