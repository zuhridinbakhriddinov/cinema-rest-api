package uz.pdp.appcinemarest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// Zuhridin Bakhriddinov 3/14/2022 8:04 PM
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DistributorDto {
    @NotNull
    @Size(min = 3,max = 250)
    private String name;


    @NotNull
    @Size(min = 3,max = 250)
    private String description;


}
