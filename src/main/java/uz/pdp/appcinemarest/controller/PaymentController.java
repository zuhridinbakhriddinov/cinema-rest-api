package uz.pdp.appcinemarest.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import uz.pdp.appcinemarest.mail.MailService;
import uz.pdp.appcinemarest.projection.CustomTicketForCart;
import uz.pdp.appcinemarest.repository.TransactionalHistoryRepository;
import uz.pdp.appcinemarest.repository.TicketRepository;
import uz.pdp.appcinemarest.repository.UserRepository;
import uz.pdp.appcinemarest.security.CurrentUser;
import uz.pdp.appcinemarest.service.AttachmentService;
import uz.pdp.appcinemarest.service.PaymentService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;

@RestController
public class PaymentController extends Thread {

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
    @Autowired
    MailService mailService;

    boolean success = false;


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
                success = true;
             /*   Map<String, Object> templateModel =new HashMap<>();

                templateModel.put("recipientName", optionalUser.get().getFullName());
                String serverName = "localhost";
                int port = 8080;
                Socket socket = new Socket();
                SocketAddress socketAddress = new InetSocketAddress(serverName, port);
                socket.connect(socketAddress, 12000*100);
                mailService.sendMessageUsingFreemarkerTemplate(templateModel);
      */

            }

        }

    }

    @RequestMapping("/success")
    public ModelAndView successStripePayment() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        Optional<User> user = userRepository.findById(1);
        if (success) paymentService.sendSms(user.get().getFullName());
        return modelAndView;
    }
    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
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
        double sum = 0;

        for (Integer ticketId : ticketIds) {
            Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
            sum += ticketOptional.get().getPrice() / 50;
   }
        String paymentIntent = transactionalHistoryRepository.getPaymentIntentByTicketId(ticketIds.get(0));
        TransactionalHistory transactionalHistory = transactionalHistoryRepository.findByPaymentIntent(paymentIntent);
        transactionalHistory.setRefunded(true);
        System.out.println(paymentIntent);
        RefundCreateParams params =
                RefundCreateParams
                        .builder()
                        .setPaymentIntent(paymentIntent)
                        .setAmount((long) sum)
                        .build();
        Refund.create(params);


        paymentService.changeTicketStatusToPurchase(1, TicketStatus.PURCHASED, TicketStatus.REFUNDED);
        return new ResponseEntity<String>("Success", HttpStatus.CREATED);


    }


    @PostMapping("/sms")
    public HttpEntity<?> sms() {


        return ResponseEntity.ok("good");
    }

    @PostMapping("/email")
    public HttpEntity<?> email() throws IOException, TemplateException, MessagingException {
        Map<String, Object> templateModel = new HashMap<>();

        templateModel.put("recipientName", "zuhridin");
        String serverName = "localhost";
        int port = 8080;
        Socket socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(serverName, port);
        socket.connect(socketAddress, 12000 * 100);
        mailService.sendMessageUsingFreemarkerTemplate(templateModel);
        return ResponseEntity.ok("good");
    }


}

