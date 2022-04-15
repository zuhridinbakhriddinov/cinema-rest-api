package uz.pdp.appcinemarest.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentSource;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appcinemarest.entity.*;
import uz.pdp.appcinemarest.entity.enums.TicketStatus;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.payload.TicketDto;
import uz.pdp.appcinemarest.projection.CustomTicketForCart;
import uz.pdp.appcinemarest.repository.PayTypeRepository;
import uz.pdp.appcinemarest.repository.TransactionalHistoryRepository;
import uz.pdp.appcinemarest.repository.TicketRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Zuhridin Bakhriddinov 3/28/2022 12:33 AM
@Service
public class PaymentService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    TransactionalHistoryRepository transactionalHistoryRepository;
    @Autowired
    PayTypeRepository payTypeRepository;

    @Value("${AUTH_TOKEN}")
    String AUTH_TOKEN;

    @Value("${ACCOUNT_SID}")
    String ACCOUNT_SID;
    @SneakyThrows
    public void refundOrder(List<Ticket> allByCartIdAndStatus) {
        for (Ticket ticket : allByCartIdAndStatus) {
            ticket.setTicketStatus(TicketStatus.REFUNDED);
            ticketRepository.save(ticket);

        }

    }
public void sendSms(String fullName){
                  Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                Message message = Message.creator(
                                new com.twilio.type.PhoneNumber("+998930870308"),
                                "MG799d4bb90f90006e6c4b76e0b6a2b5f1",
                                fullName+" your ticket(s) successfully purchased!")
                        .create();
                message.getDirection();
                System.out.println(message);
}


    public void fulfillOrder(Session session) {
/*        Optional<PayType> payTypeRepositoryById = payTypeRepository.findById(1);

        double summa=0;
        for (Ticket ticket : allByCartIdAndStatus) {

            ticket.setTicketStatus(TicketStatus.PURCHASED);
            summa+=ticket.getPrice();
        }
        List<Ticket> tickets = ticketRepository.saveAll(allByCartIdAndStatus);

        TransactionalHistory transactionalHistory = new TransactionalHistory();
        transactionalHistory.setPaymentIntent(paymentIntent);
        transactionalHistory.setStatus(TicketStatus.PURCHASED);
        transactionalHistory.setTicketList(tickets);
        transactionalHistory.setTotalAmount(summa);
        transactionalHistory.setPayType(payTypeRepositoryById.get());
        transactionalHistoryRepository.save(transactionalHistory);*/
        addTransactionHistory(Integer.valueOf(session.getClientReferenceId()), session.getPaymentIntent());
        changeTicketStatusToPurchase(Integer.valueOf(session.getClientReferenceId()),TicketStatus.NEW,TicketStatus.PURCHASED);

    }

    public ResponseEntity<?> getStripeSession(User user, List<SessionCreateParams.LineItem> lineItems, List<CustomTicketForCart> ticketList) {
        for (CustomTicketForCart ticket : ticketList) {
            double ticketPrice = (ticket.getPrice()*100+30)/(1-2.9/100);
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

            return ResponseEntity.ok(checkoutUrl);

        } catch (StripeException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @Transactional
    public void addTransactionHistory(Integer userId, String paymentIntent) {
        //  List<Ticket> ticketList = ticketRepository.findByUserIdAndTicketStatus(userId, TicketStatus.NEW);
        List<Ticket> ticketList =
                ticketRepository.findAllByUserIdAndTicketStatus(userId, TicketStatus.NEW);

        double totalAmount = ticketList.stream().map(Ticket::getPrice).
                collect(Collectors.toList()).stream().mapToDouble(value -> value).sum();


        TransactionalHistory transactionHistory = new TransactionalHistory();
        transactionHistory.setTicketList(ticketList);
        transactionHistory.setTotalAmount(totalAmount);
        transactionHistory.setPaymentIntent(paymentIntent);
        transactionHistory.setRefunded(false);

        transactionalHistoryRepository.save(transactionHistory);
    }

    public boolean changeTicketStatusToPurchase(Integer userId,TicketStatus firstTicketStatus,TicketStatus secondTicketStatus) {

        try {
            List<Ticket> ticketList =
                    ticketRepository.findAllByUserIdAndTicketStatus(userId, firstTicketStatus);
            for (Ticket ticket : ticketList) {
                ticket.setTicketStatus(secondTicketStatus);
            }
            ticketRepository.saveAll(ticketList);

            return true;
        } catch (Exception e) {
            e.printStackTrace();


        }
        return false;
    }


}
