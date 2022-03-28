package uz.pdp.appcinemarest.payload;

// Zuhridin Bakhriddinov 3/27/2022 10:12 PM

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class SessionDto {
    private Integer movieAnnouncementId;
    private List<ReservedHallDto> reservedHallDtoList;

}
