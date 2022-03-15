package uz.pdp.appcinemarest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Zuhridin Bakhriddinov 3/15/2022 12:54 AM
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActorDto {
    private String name;
    private Integer photoId;

}
