package uz.pdp.appcinemarest.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.appcinemarest.entity.*;
import uz.pdp.appcinemarest.entity.enums.TicketStatus;
import uz.pdp.appcinemarest.projection.CustomTicketForCart;
import uz.pdp.appcinemarest.repository.PurchaseHistoryRepository;
import uz.pdp.appcinemarest.repository.TicketRepository;
import uz.pdp.appcinemarest.repository.UserRepository;
import uz.pdp.appcinemarest.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PaymentController {

    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/success")
    public ModelAndView successStripePayment() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        return modelAndView;
    }


    @SneakyThrows
    @PostMapping("/webhook")
    public void handle(@RequestBody String payload, @RequestHeader(name = "Stripe-Signature") String signHeader, HttpServletResponse response) {
        String endpointSecret = "whsec_caf7231c252f6538d81bf44b2f1ee721c5b42440b2211aa88ed4dbc1aa3393b8";
        Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";
//      to activate:  stripe listen --forward-to localhost:8080/webhook
        Event event = Webhook.constructEvent(payload, signHeader, endpointSecret);

        System.out.println("Order fulfilled");
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().get();


            //      Optional<Cart> optionalCart = cartRepository.findById(Long.valueOf(session.getClientReferenceId()));
            Optional<User> optionalUser = userRepository.findById(Integer.valueOf(session.getClientReferenceId()));
            List<Ticket> allByCartIdAndStatus =
                    ticketRepository.findAllByUserIdAndTicketStatus(optionalUser.get().getId(), TicketStatus.NEW);
            if (allByCartIdAndStatus.size() != 0) {
                fulfillOrder(optionalUser.get(), allByCartIdAndStatus, session.getPaymentIntent());
            }
        }

    }

    @SneakyThrows
    private void fulfillOrder(User user, List<Ticket> allByCartIdAndStatus, String paymentIntent) {
        for (Ticket ticket : allByCartIdAndStatus) {
            ticket.setTicketStatus(TicketStatus.PURCHASED);
            /*Attachment attachment = attachmentService.generateTicketPdf(ticket, cart);
            ticket.setQrCode(attachment);*/
            ticketRepository.save(ticket);

            purchaseHistoryRepository.save(new PurchaseHistory(user, ticket, LocalDateTime.now(), paymentIntent));
        }

    }

    @SneakyThrows
    private void refundOrder(List<Ticket> allByCartIdAndStatus) {
        for (Ticket ticket : allByCartIdAndStatus) {
            ticket.setTicketStatus(TicketStatus.REFUNDED);
            /*Attachment attachment = attachmentService.generateTicketPdf(ticket, cart);
            ticket.setQrCode(attachment);*/
            ticketRepository.save(ticket);

        }

    }


    @GetMapping("/charge")
    public HttpEntity<?> createStripeSession() {
        Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";

        Optional<User> optionalUser = userRepository.findById(1);
        User user = optionalUser.get();
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        List<CustomTicketForCart> ticketList = ticketRepository.getTicketByUserId(user.getId());

        for (CustomTicketForCart ticket : ticketList) {
            double ticketPrice = ticket.getPrice();
            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
                    .builder()
                    .setName(ticket.getMovieTitle())
                    .build();

            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData
                    .builder()
                    .setProductData(productData)
                    .setCurrency("usd")
                    .setUnitAmount((long) (ticketPrice * 100))
                    .build();

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem
                    .builder()
                    .setPriceData(priceData)
                    .setQuantity(1L)
                    .build();


            lineItems.add(lineItem);


        }
  /*      SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl("http://localhost:8080/cancel")
                .setSuccessUrl("http://localhost:8080/success")
                .addAllLineItem(lineItems)
                .setClientReferenceId(user.getId().toString())
                .build();

        Session session = Session.create(params);
        return ResponseEntity.ok().build();*/

        SessionCreateParams params = SessionCreateParams
                .builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl("http://localhost:8080/failed")
                .setSuccessUrl("http://localhost:8080/success")
                .setClientReferenceId(user.getId().toString())
                .addAllLineItem(lineItems)

                .build();
        try {
            Session session = Session.create(params);
            String checkoutUrl = session.getUrl();

            //returning checkout url of session
            return ResponseEntity.ok(checkoutUrl);

        } catch (StripeException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/refund")
    public HttpEntity createCharge() throws StripeException {
        Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";
      /*  Optional<PurchaseHistory> optionalPurchaseHistory = purchaseHistoryRepository.findById(purchaseHistoryId);
        PurchaseHistory purchaseHistory = optionalPurchaseHistory.get();
        Ticket ticket = purchaseHistory.getTicket();
        ticket.setTicketStatus(TicketStatus.REFUNDED);
        purchaseHistoryRepository.save(purchaseHistory);*/

        List<PurchaseHistory> purchaseHistoryRepositoryByUserId = purchaseHistoryRepository.findByUserId(1);
        for (PurchaseHistory purchaseHistory : purchaseHistoryRepositoryByUserId) {

            RefundCreateParams params =
                    RefundCreateParams
                            .builder()
                            .setPaymentIntent(purchaseHistory.getChargeId())
                            .setAmount(10*100L)
                            .build();
            Refund.create(params);
        }




            //      Optional<Cart> optionalCart = cartRepository.findById(Long.valueOf(session.getClientReferenceId()));

            List<Ticket> allByCartIdAndStatus =
                    ticketRepository.findAllByUserIdAndTicketStatus(1, TicketStatus.PURCHASED);
            if (allByCartIdAndStatus.size() != 0) {
                refundOrder(allByCartIdAndStatus);
            }


        return new ResponseEntity<String>("Success", HttpStatus.CREATED);


    }


}

