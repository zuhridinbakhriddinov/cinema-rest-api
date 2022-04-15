package uz.pdp.appcinemarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcinemarest.payload.LoginDto;
import uz.pdp.appcinemarest.payload.RegisterDto;
import uz.pdp.appcinemarest.service.AuthService;

import javax.validation.Valid;

// Zuhridin Bakhriddinov 4/5/2022 11:48 AM
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        return authService.registerUser(registerDto);


    }

    @PostMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String sendingEmail) {
        authService.verifyEmail(emailCode, sendingEmail);

        return ResponseEntity.ok().build();

    }
    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
      return   authService.login(loginDto);



    }

}
