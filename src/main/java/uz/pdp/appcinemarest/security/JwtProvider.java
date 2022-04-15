package uz.pdp.appcinemarest.security;


import com.twilio.exception.RestException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${secret-key}")
    private String secretKey;

    @Value("${expire_access_token}")
    private long accessTokenExpire;

    @Value("${refresh_token}")
    private long refreshTokenExpire;

    public String generateToken(String email, boolean forAccess) {
        Date expire = new Date(System.currentTimeMillis() + (forAccess ? accessTokenExpire : refreshTokenExpire));
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //or HS384 or HS512
        return Jwts
                .builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)))
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expire)
                .compact();
    }

    public String getEmail(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    public void validateToken(String token){
         Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
    }
}
