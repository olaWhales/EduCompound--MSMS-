package org.example.parentPackage.parentService;

public interface ParentVerificationService {
    void verifyToken(String email, String token, String password);
    void resendVerificationToken(String email);
}