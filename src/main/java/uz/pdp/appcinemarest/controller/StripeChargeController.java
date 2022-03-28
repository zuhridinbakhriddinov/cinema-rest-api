package uz.pdp.appcinemarest.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Balance;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcinemarest.entity.PurchaseHistory;
import uz.pdp.appcinemarest.entity.StripeCharge;
import uz.pdp.appcinemarest.entity.Ticket;
import uz.pdp.appcinemarest.entity.enums.TicketStatus;
import uz.pdp.appcinemarest.repository.PurchaseHistoryRepository;
import uz.pdp.appcinemarest.repository.TicketRepository;
import uz.pdp.appcinemarest.service.StripeChargeService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Zuhridin Bakhriddinov 3/27/2022 1:29 PM
@RestController
@RequestMapping("/payment")
public class StripeChargeController {

    @Autowired
    StripeChargeService stripeChargeService;
    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired
    TicketRepository ticketRepository;


    @PostMapping("/charge/{ticketId}")
    public HttpEntity createCharge(@PathVariable Integer ticketId,@RequestParam Integer paymentId) {

        return stripeChargeService.paymentOperation(ticketId,paymentId);
    }

    @PostMapping("/refund/{purchaseHistoryId}")
    public HttpEntity createCharge(@PathVariable Integer purchaseHistoryId) throws StripeException {
        Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";
        Optional<PurchaseHistory> optionalPurchaseHistory = purchaseHistoryRepository.findById(purchaseHistoryId);
        PurchaseHistory purchaseHistory = optionalPurchaseHistory.get();
        Ticket ticket = purchaseHistory.getTicket();
        ticket.setTicketStatus(TicketStatus.REFUNDED);
        purchaseHistoryRepository.save(purchaseHistory);
//        Map<String, Object> params = new HashMap<>();
//        params.put(
//                "charge",
//                purchaseHistory.getChargeId()
//        );
//        params.put("amount", 10L);
        RefundCreateParams params =
                RefundCreateParams
                        .builder()
                        .setCharge(purchaseHistory.getChargeId())
                        .setAmount(10L)
                        .build();

        Refund refund = Refund.create(params);


        return new ResponseEntity<String>("Success", HttpStatus.CREATED);


    }

}
