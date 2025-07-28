package org.example.tokenPackage;

import org.example.data.model.AdminTenant;

public interface TokenService {
    String generateSixDigitToken();

    boolean validateToken(String email, String token);

    void markEmailAsVerified(String email);

    void markEmailAsVerified(String email, String token);

//    boolean resendToken(String email);

    void saveToken(String email, String newToken, AdminTenant adminTenant);
}
