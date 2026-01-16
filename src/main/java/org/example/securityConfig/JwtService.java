package org.example.securityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.data.model.Users;
import org.example.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

//@Service
//public class JwtService {
//    private final UserRepository userRepository;
//    private final String secreteKey;
//
//    public JwtService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256"); // Fixed typo
//            SecretKey sk = keyGenerator.generateKey();
//            this.secreteKey = Base64.getEncoder().encodeToString(sk.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//public String GenerateToken(String username) {
//    Users user = userRepository.findByEmail(username)
//            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//    Map<String, Object> claims = new HashMap<>();
//    claims.put("roles", List.of("ROLE_" + user.getRole().name())); // important!
//
//    return Jwts.builder()
//            .claims()
//            .add(claims)
//            .subject(username)
//            .issuedAt(new Date(System.currentTimeMillis()))
//            .expiration(new Date(System.currentTimeMillis() + 2000 * 60 * 60 * 10))
//            .and()
//            .signWith(getkey())
//            .compact();
//}
//
//    private SecretKey getkey() {
//        byte[] keyByte = Decoders.BASE64.decode(secreteKey);
//        return Keys.hmacShaKeyFor(keyByte);
//    }
//    public String extractUsername(String token) {
//        return extractClaim(token , Claims::getSubject);
//    }
//    private <T> T extractClaim(String token , Function<Claims, T> claimsResolver){
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//    Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .verifyWith(getkey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//    public boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token , Claims::getExpiration);
//    }
//
//    @Override
//    public String toString() {
//        return "JwtService{" +
//                "secreteKey='" + secreteKey + '\'' +
//                '}';
//    }
//}
@Service
public class JwtService {

    private final UserRepository userRepository;
    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtService(
            UserRepository userRepository,
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs
    ) {
        this.userRepository = userRepository;
        this.expirationMs = expirationMs;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    Instant now = Instant.now();

    public String generateToken(String username) {

        Instant now = Instant.now();

        Users user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of("ROLE_" + user.getRole().name()));

        return Jwts.builder()
                .claims(claims)
                .subject(username)
//                .issuedAt(new Date())
                .issuedAt(Date.from(now))
//                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .expiration(Date.from(now.plus(7, ChronoUnit.DAYS)))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return extractAllClaims(token).map(resolver).orElseThrow();
    }

//    public Instant extractIssuedAt(String token) {
//        return extractAllClaims(token).getIssuedAt().toInstant();
//    }
    public Instant extractIssuedAt(String token) {
        return extractAllClaims(token)
                .map(Claims::getIssuedAt)
                .map(Date::toInstant)
                .orElseThrow(() -> new IllegalStateException("JWT has no issued-at"));
    }


    private Optional<Claims> extractAllClaims(String token) {
        return Optional.of(
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
        );
    }
}
