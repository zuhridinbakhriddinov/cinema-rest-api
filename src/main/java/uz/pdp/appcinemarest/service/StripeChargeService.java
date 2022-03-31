package uz.pdp.appcinemarest.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentSource;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.appcinemarest.entity.PayType;
import uz.pdp.appcinemarest.entity.PurchaseHistory;
import uz.pdp.appcinemarest.entity.StripeCharge;
import uz.pdp.appcinemarest.entity.Ticket;
import uz.pdp.appcinemarest.entity.enums.TicketStatus;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.payload.TicketDto;
import uz.pdp.appcinemarest.repository.PayTypeRepository;
import uz.pdp.appcinemarest.repository.PurchaseHistoryRepository;
import uz.pdp.appcinemarest.repository.TicketRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Zuhridin Bakhriddinov 3/28/2022 12:33 AM
@Service
public class StripeChargeService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    StripeCharge stripeCharge;
    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired
    PayTypeRepository payTypeRepository;

 /*   public HttpEntity paymentOperation(Integer ticketId,Integer paymentId) {
        try {
            Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
            if (!ticketOptional.isPresent()) {
                return new ResponseEntity(new ApiResponse("wrong",
                        false, null), HttpStatus.BAD_REQUEST);
            }
            Ticket ticket = ticketOptional.get();

            Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";
            stripeCharge.setAmount((long) (ticket.getPrice() * 100));
            stripeCharge.setReceiptEmail(ticket.getUser().getUserName());
            Charge charge = Charge.create(stripeCharge.getCharge());
            charge.setCustomer("cus_LOdFKQJoi6XccW");



            System.out.println(charge.getId());
            ticket.setTicketStatus(TicketStatus.PURCHASED);

            PurchaseHistory purchaseHistory=new PurchaseHistory();
            Optional<PayType> payTypeOptional = payTypeRepository.findById(paymentId);
            if (!payTypeOptional.isPresent()) {
                return new ResponseEntity(new ApiResponse("wrong",
                        false, null), HttpStatus.BAD_REQUEST);
            }
            purchaseHistory.setDate(LocalDateTime.now());
            purchaseHistory.setPayType(payTypeOptional.get());
            purchaseHistory.setUser(ticket.getUser());
            purchaseHistory.setTicket(ticket);
            purchaseHistory.setChargeId(charge.getId());
            purchaseHistoryRepository.save(purchaseHistory);
            System.out.println(charge);
            return new ResponseEntity<String>("Success", HttpStatus.CREATED);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }*/

    public ApiResponse checkoutTickets(List<TicketDto> ticketList, Integer userId) throws StripeException, IOException {
        Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for (TicketDto ticket : ticketList) {
            lineItems.add(createLineItem(ticket));
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl("http://localhost:8080/cancel")
                .setSuccessUrl("http://localhost:8080/success")
                .addAllLineItem(lineItems)
                .setClientReferenceId(userId.toString())
                .build();

        Session session = Session.create(params);
        return new ApiResponse("You will be redirected to the Stripe Check out Page. Please fill your visa card information to do the payment", true, session.getUrl());
    }

    private SessionCreateParams.LineItem createLineItem(TicketDto ticket) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(ticket))
                .setQuantity(1L)
                .build();

    }

    private SessionCreateParams.LineItem.PriceData createPriceData(TicketDto ticket) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long) ticket.getPrice() * 100)
                .setProductData(createProductData(ticket))
                .build();

    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(TicketDto ticket) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(ticket.getMovieName())
                .addImage("https://media.istockphoto.com/vectors/pair-of-tickets-to-a-movie-show-or-other-entertainment-event-vector-id1276509148?k=20&m=1276509148&s=612x612&w=0&h=pieGPJsxRNTfeK5yyzAkF04LI2XSO6ABiN_4vc-I6DY=")
                .build();
    }


}
