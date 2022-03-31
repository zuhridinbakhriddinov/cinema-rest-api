package uz.pdp.appcinemarest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

// Zuhridin Bakhriddinov 3/30/2022 10:30 AM
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDto {
    private Integer id;
    private String MovieName;
    private double price;
    private Integer qrCodeId;
}
