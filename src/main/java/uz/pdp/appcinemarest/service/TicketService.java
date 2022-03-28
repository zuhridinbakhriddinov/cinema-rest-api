package uz.pdp.appcinemarest.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.appcinemarest.entity.*;
import uz.pdp.appcinemarest.entity.enums.TicketStatus;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.projection.CustomTicketForCart;
import uz.pdp.appcinemarest.repository.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

// Zuhridin Bakhriddinov 3/26/2022 11:08 PM
@Service
public class TicketService {

    @Autowired
    WaitingPurchaseTimeRepository waitingPurchaseTimeRepository;

    @Autowired
    MovieAnnouncementRepository movieAnnouncementRepository;

    @Autowired
    MovieSessionRepository movieSessionRepository;
    @Autowired
    SeatRepository seatRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;


    public byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    public HttpEntity generationTicket(Integer movieSessionId,
                                       Integer seatId,
                                       Integer userId) throws IOException, WriterException {

        Optional<MovieSession> movieSessionRepositoryById = movieSessionRepository.findById(movieSessionId);
        Optional<Seat> seatOptional = seatRepository.findById(seatId);


        Optional<User> userOptional = userRepository.findById(userId);

        Double ticketPriceByMovieSessionIdAndSeatId = ticketRepository.getTicketPriceByMovieSessionIdAndSeatId(movieSessionId, seatId);


        Ticket ticket = new Ticket(movieSessionRepositoryById.get(), seatOptional.get(), null, ticketPriceByMovieSessionIdAndSeatId, TicketStatus.NEW, userOptional.get());

        byte[] qrCodeImage = getQRCodeImage(ticket.getSerialNumber(), 200, 200);
        Attachment attachment = new Attachment();
        attachment.setName(ticket.getSerialNumber());
        attachment.setContentType("image/png");
        AttachmentContent attachmentContent = new AttachmentContent(attachment, qrCodeImage);
        attachmentContentRepository.save(attachmentContent);
        attachmentRepository.save(attachment);
        ticket.setQrCode(attachment);
        boolean b = ticketRepository.existsBySeatId(seatId);
        if (b)
            return new ResponseEntity(new ApiResponse("wrong",
                    false, null), HttpStatus.BAD_REQUEST);
        else {
            ticketRepository.save(ticket);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    CustomTicketForCart ticketByIdForCart = ticketRepository.getTicketByIdForCart(ticket.getId());
                    try {
                        if (ticketByIdForCart.getTicketStatus().equals(TicketStatus.NEW)) {
                            ticketRepository.deleteById(ticket.getId());
                            System.out.println("Ticket is deleted " + ticket.getId());
                        }
                    } catch (NullPointerException ignored) {
                    }
                }
            };
            Timer timer = new Timer();
            System.out.println("after " + waitingPurchaseTimeRepository.getWaitingMinute() + " minutes ticket will be deleted!");
            timer.schedule(timerTask, waitingPurchaseTimeRepository.getWaitingMinute() * 60000);
            return new ResponseEntity(new ApiResponse("Success",
                    true, true), HttpStatus.OK);
        }
    }


}
