package org.example.utilities;

import java.security.SecureRandom;

public class Utilities {
    public static String REGISTRATION_SUCCESS = "Registration successful";

    public static String generateSixDigitToken() {
        SecureRandom random = new SecureRandom();
        int token = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(token);
    }
}