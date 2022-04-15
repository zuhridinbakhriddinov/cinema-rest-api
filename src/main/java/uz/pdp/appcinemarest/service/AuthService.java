package uz.pdp.appcinemarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appcinemarest.entity.User;
import uz.pdp.appcinemarest.entity.enums.Role_enum;
import uz.pdp.appcinemarest.payload.ApiResponse;
import uz.pdp.appcinemarest.payload.LoginDto;
import uz.pdp.appcinemarest.payload.RegisterDto;
import uz.pdp.appcinemarest.repository.RoleRepository;
import uz.pdp.appcinemarest.repository.UserRepository;
import uz.pdp.appcinemarest.security.JwtProvider;

import javax.mail.internet.MimeMessage;
import java.net.Socket;
import java.util.*;

// Zuhridin Bakhriddinov 4/5/2022 11:45 AM
@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public HttpEntity registerUser(RegisterDto registerDto) {

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>(new ApiResponse("Wrong", false, false), HttpStatus.ALREADY_REPORTED);
        }
        User user = new User(registerDto.getFullName(), registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword()), roleRepository.findByName(Role_enum.ROLE_USER)
        );
        user.setEmailCode(UUID.randomUUID().toString());
        Boolean aBoolean = sendEmail(user.getFullName(), user.getEmail(), user.getEmailCode());
        if (!aBoolean) {
            user.setEnabled(true);
            userRepository.save(user);

            return new ResponseEntity<>(new ApiResponse("Email not send to user but account activated.", false, false), HttpStatus.BAD_REQUEST);

        }


        return new ResponseEntity<>(new ApiResponse("Successfully Registered. Please confirm your email in order to activate!", true, true), HttpStatus.OK);


    }

    private Boolean sendEmail(String userFirstName, String sendingEmail, String emailCode) {
        try {
            String serverName = "localhost";
            int port = 8080;

// set the socket SO timeout to 10 seconds
            Socket socket = new Socket(serverName, port);
            socket.setSoTimeout(10 * 1000);
            String link = "http://localhost:8080/auth/verifyEmail?emailCode=" + emailCode + "&sendingEmail=" + sendingEmail;

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(buildEmail(userFirstName, link), true);
            helper.setTo("zukicgleo@gmail.com");
            helper.setSubject("Confirm your email");
            helper.setFrom("zuhridin");
            javaMailSender.send(mimeMessage);


            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String buildEmail(String name, String link) {
        return link;
    }

    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("Account is confirmed", true);
        }
        return new ApiResponse("Account already confirmed", false);
    }

    public HttpEntity login(LoginDto loginDto) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword()
            ));

            User principal = (User) authentication.getPrincipal();
            String generatedToken = jwtProvider.generateToken(principal.getEmail(), true);
            return new ResponseEntity<>(new ApiResponse("Token", true, generatedToken), HttpStatus.OK);


        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ApiResponse("Email or password not found", false, false), HttpStatus.NOT_FOUND);

        }
    }

    public void processOAuthPostLogin(String username) {
        Optional<UserDetails> existUser = userRepository.findByEmail(username);



    }

}






