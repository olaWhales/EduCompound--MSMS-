package org.example.utilities;

import java.security.SecureRandom;

public class Utilities {
    public static String REGISTRATION_SUCCESS = "Registration successful";
    public static String AUTHENTICATION_REQUIRE_MESSAGE = "Authentication required with UserPrincipal";
    public static String AUTHENTICATION_NOT_FOUND_MESSAGE = "Authenticated user not found";
    public static String USER_IS_NOT_AN_ADMIN_MESSAGE = "User is not an admin";
    public static String ADMIN_TENANT_NOT_FOUND = "AdminTenant not found for user";
    public static String TEACHER_REGISTRATION_SUCCESSFUL_MESSAGE = "Teacher Registration Invitation";
    public static String USER_NOT_FOUND_MESSAGE = "User not found: ";
    public static String PASSWORD_ALREADY_SET_FOR_THE_USER_MESSAGE = "Password already set for user: ";
    public static String TOKEN_HAS_EXPIRE_MESSAGE = "Token has expired";
    public static String INVALID_OR_EXPIRE_TOKEN = "Invalid or expired token";
    public static String PASSWORD_IS_REQUIRE_MESSAGE = "Password is required";
    public static String ADMIN_ALREADY_REGISTERED_MESSAGE = "Admin email already registered";

    public static String REGISTRATION_INVITATION_MESSAGE = "<html>" +
            "<body>" +
            "<h2>Teacher Registration Invitation</h2>" +
            "<p>You have been invited to join as a teacher at %s.</p>" +
            "<p><strong>Token:</strong> %s</p>" +
            "<p><strong>Registration Link:</strong> <a href='%s'>Click here to register</a></p>" +
            "<p>This link will expire in 24 hours.</p>" +
            "<p>If you did not request this, please ignore this email.</p>" +
            "</body>" +
            "</html>";

    public static String generateSixDigitToken() {
        SecureRandom random = new SecureRandom();
        int token = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(token);
    }
}