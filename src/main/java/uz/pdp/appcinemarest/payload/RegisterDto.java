package uz.pdp.appcinemarest.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

// Zuhridin Bakhriddinov 4/5/2022 11:50 AM
@Data
public class RegisterDto {

    @NotNull
    private String fullName;
    @NotNull
    private String email;

    @NotNull
    private String password;


}
