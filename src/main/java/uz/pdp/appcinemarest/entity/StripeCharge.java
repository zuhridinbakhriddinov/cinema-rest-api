package uz.pdp.appcinemarest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// Zuhridin Bakhriddinov 3/27/2022 1:27 PM
@Component
@Data
public class StripeCharge {
    private  Long amount;
    private  String receiptEmail;
    private  String source="tok_visa";
    private  String currency="usd";

    public StripeCharge(Long amount, String receiptEmail, String source, String currency) {
        this.amount = amount;
        this.receiptEmail = receiptEmail;
        this.source = source;
        this.currency = currency;
    }

    public StripeCharge() {
    }


    public Map<String, Object> getCharge() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", this.amount);
        params.put("currency", this.currency);
        params.put("source", this.source);
        params.put(
                "description",
                "My First Test Charge (created for API docs)"
        );
        params.put("receipt_email",this.receiptEmail);
        return params;
    }
}
