package uz.pdp.appcinemarest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Zuhridin Bakhriddinov 3/17/2022 5:57 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieAnnouncementDto {
    private Integer movieId;
    private Boolean isActive;


}
