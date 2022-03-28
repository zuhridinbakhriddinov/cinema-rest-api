package uz.pdp.appcinemarest.payload;

// Zuhridin Bakhriddinov 3/27/2022 10:11 PM

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservedHallDto {
    private Integer hallId;
    private String startDate;
    private String endDate;
    private List<String> startTimeList;


}
