package org.example.securityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private String secreteKey = "";
    public JwtService() {
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHa256");
            SecretKey sk = keyGenerator.generateKey();
            secreteKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
     public String GenerateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
         return Jwts.builder()
                 .claims()
                 .add(claims)
                 .subject(username)
                 .issuedAt(new Date(System.currentTimeMillis()))
                 .expiration(new Date(System.currentTimeMillis() + 2000 * 60 * 60 * 10 ))
                 .and()
                 .signWith(getkey())
                 .compact();
     }
    private SecretKey getkey() {
        byte[] keyByte = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(keyByte);
    }
    public String extractUsername(String token) {
        return extractClaim(token , Claims::getSubject);
    }
    private <T> T extractClaim(String token , Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token , Claims::getExpiration);
    }

    @Override
    public String toString() {
        return "JwtService{" +
                "secreteKey='" + secreteKey + '\'' +
                '}';
    }
}
