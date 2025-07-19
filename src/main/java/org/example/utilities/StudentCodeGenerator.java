package org.example.utilities;

import java.security.SecureRandom;

public class StudentCodeGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static String generateStudentCode(String className) {
        if (className == null || className.isBlank()) {
            throw new IllegalArgumentException("Class name must not be null or empty.");
        }

        // Normalize class name (e.g., trim and uppercase)
        String prefix = className.trim().toUpperCase().replaceAll("\\s+", "");

        // Generate random 4-digit number
        int number = 1000 + random.nextInt(9000); // ensures number is between 1000 and 9999

        return prefix + "-" + number;
    }
}
