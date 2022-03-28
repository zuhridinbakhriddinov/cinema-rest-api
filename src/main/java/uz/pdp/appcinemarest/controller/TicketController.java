package uz.pdp.appcinemarest.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcinemarest.entity.*;
import uz.pdp.appcinemarest.entity.enums.TicketStatus;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.projection.CustomTicketForCart;
import uz.pdp.appcinemarest.repository.*;
import uz.pdp.appcinemarest.service.TicketService;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

// Zuhridin Bakhriddinov 3/25/2022 11:08 AM
@RestController
@RequestMapping(value = "/addToCart")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @SneakyThrows
    @PostMapping("/{userId}")
    public HttpEntity<?> addTicket(@RequestParam("movieSessionId") Integer movieSessionId,
                                   @RequestParam("seatId") Integer seatId, @PathVariable Integer userId) {

        return ticketService.generationTicket(movieSessionId, seatId, userId);

    }


}


