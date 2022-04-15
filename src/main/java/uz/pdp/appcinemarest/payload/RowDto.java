package uz.pdp.appcinemarest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Zuhridin Bakhriddinov 4/9/2022 1:50 PM
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RowDto {
    private Integer id;
    private int number;
    private Integer hallId;
}
