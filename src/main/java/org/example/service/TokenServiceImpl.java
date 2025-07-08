package org.example.service;

import java.security.SecureRandom;

import org.example.utilities.Utilities;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String generateSixDigitToken() {
        return Utilities.generateSixDigitToken();
    }
}