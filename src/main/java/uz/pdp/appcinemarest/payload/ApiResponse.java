package uz.pdp.appcinemarest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Zuhridin Bakhriddinov 3/10/2022 11:36 PM
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private String message;
    private boolean success;
    private Object data;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
