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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.appcinemarest.entity.TransactionalHistory;
import uz.pdp.appcinemarest.entity.Ticket;
import uz.pdp.appcinemarest.entity.User;
import uz.pdp.appcinemarest.entity.enums.TicketStatus;
import uz.pdp.appcinemarest.projection.CustomTicketForCart;
import uz.pdp.appcinemarest.repository.TransactionalHistoryRepository;
import uz.pdp.appcinemarest.repository.TicketRepository;
import uz.pdp.appcinemarest.repository.UserRepository;
import uz.pdp.appcinemarest.service.AttachmentService;
import uz.pdp.appcinemarest.service.PaymentService;

import javax.servlet.http.HttpServletResponse;
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
    TransactionalHistoryRepository transactionalHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentService paymentService;
    @Autowired
    JavaMailSender javaMailSender;

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
            Optional<User> optionalUser = userRepository.findById(Integer.valueOf(session.getClientReferenceId()));
            List<Ticket> allByCartIdAndStatus =
                    ticketRepository.findAllByUserIdAndTicketStatus(optionalUser.get().getId(), TicketStatus.NEW);
            if (allByCartIdAndStatus.size() != 0) {
                paymentService.fulfillOrder(session);
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setFrom("leozukich@gmail.com");
                simpleMailMessage.setTo("leozukich@gmail.com");
                simpleMailMessage.setSubject("Hello what's up");
                simpleMailMessage.setText("are you sleeping");
                javaMailSender.send(simpleMailMessage);

            }
        }

    }


    @GetMapping("/charge")
    public HttpEntity<?> createStripeSession() {
        Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";
        Optional<User> optionalUser = userRepository.findById(1);
        User user = optionalUser.get();
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        List<CustomTicketForCart> ticketList = ticketRepository.getTicketByUserId(user.getId());

        return paymentService.getStripeSession(user, lineItems, ticketList);
    }


    @PostMapping("/refund")
    public HttpEntity createCharge(@RequestBody List<Integer> ticketIds) throws StripeException {
        Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";

        String paymentIntent = transactionalHistoryRepository.getPaymentIntentByTicketId(ticketIds.get(0));
        System.out.println(paymentIntent);
        RefundCreateParams params =
                RefundCreateParams
                        .builder()
                        .setPaymentIntent(paymentIntent)
                        .setAmount(10 * 100L)
                        .build();
        Refund.create(params);


        List<Ticket> allByCartIdAndStatus =
                ticketRepository.findAllByUserIdAndTicketStatus(1, TicketStatus.PURCHASED);
        if (allByCartIdAndStatus.size() != 0) {
            paymentService.refundOrder(allByCartIdAndStatus);
        }
        return new ResponseEntity<String>("Success", HttpStatus.CREATED);


    }


}

