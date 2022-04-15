package uz.pdp.appcinemarest.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

// Zuhridin Bakhriddinov 4/5/2022 6:15 PM
@Data
public class LoginDto {

    @NotNull
    String password;

    @NotNull
    String email;
}
