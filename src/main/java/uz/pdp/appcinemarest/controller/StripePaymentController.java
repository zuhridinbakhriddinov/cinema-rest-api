package uz.pdp.appcinemarest.controller;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Zuhridin Bakhriddinov 3/31/2022 9:39 AM
@RestController
@RequestMapping("/api/stripe-webhook")
public class StripePaymentController {



    @PostMapping()
    public Object session(@RequestBody String payload){
        Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";


        return "";

    }
}
