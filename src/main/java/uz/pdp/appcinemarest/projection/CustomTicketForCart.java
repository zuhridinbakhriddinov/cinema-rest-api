package uz.pdp.appcinemarest.projection;

import uz.pdp.appcinemarest.entity.enums.TicketStatus;

public interface CustomTicketForCart {
    Integer getTicketId();
    TicketStatus getTicketStatus();
    String getMovieTitle();
    Double getPrice();

}
