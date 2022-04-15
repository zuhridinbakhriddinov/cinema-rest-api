package uz.pdp.appcinemarest.payload;

import lombok.Data;

// Zuhridin Bakhriddinov 4/10/2022 11:38 PM
@Data
public class MovieSessionDto {

    private Integer movieAnnouncementId;

    private Integer hallId;

    private Integer startDateId;

    private Integer startTimeId;

    private Integer endTimeId;
}
