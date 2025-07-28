package org.example.tokenPackage;

import lombok.RequiredArgsConstructor;
import org.example.data.model.AdminTenant;
import org.example.data.model.Token;
import org.example.data.repositories.TokenRepository;
import org.example.utilities.Utilities;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Override
    public String generateSixDigitToken() {
        return Utilities.generateSixDigitToken();
    }

    @Override
    public boolean validateToken(String email, String token) {
        return tokenRepository.findByEmailAndToken(email, token)
                .filter(t -> !t.isExpired() && !t.isVerified())
                .isPresent();
    }

    @Override
    public void markEmailAsVerified(String email) {

    }

    @Override
    public void markEmailAsVerified(String email, String token) {
        tokenRepository.findByEmailAndToken(email, token).ifPresent(t -> {
            t.setVerified(true);
            tokenRepository.save(t);
        });
    }


//    @Override
//    public boolean resendToken(String email) {
//        // Optional: if you want to limit resend frequency
//        return false;
//    }

    @Override
    public void saveToken(String email, String newToken, AdminTenant adminTenant) {
        Token token = Token.builder()
                .email(email)
                .token(newToken)
                .adminTenant(adminTenant)
                .createdAt(new Date())
                .expiresAt(calculateExpiryDate(15)) // 15 mins expiry
                .verified(false)
                .build();
        tokenRepository.save(token);
    }

    private Date calculateExpiryDate(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }
}
