package org.example.utilities;

import java.security.SecureRandom;

public class Utilities {
    public static final String SESSION_UPDATED_SUCCESSFULLY = "Session updated successfully";
    public static final String ONLY_COMPLETED_SESSIONS_CAN_BE_RESTORED = "Only_completed_sessions_can_be_restored" ;
    public static final String ACADEMIC_SESSION_ALREADY_EXISTS = "Academic session already exist";
    public static final String ACADEMIC_SESSION_CREATED_SUCCESSFULLY = "Academic session created successfully";
    //    public static final String DUPLICATE_SESSION_FOR_THIS_SESSION = ;
    public static String ADMIN_EMAIL_ALREADY_REGISTERED_AND_PASSWORD_IS_SET = "Admin email already registered and password is set.";
    public static String REGISTRATION_SUCCESS = "Registration successful, please check your email for the password setup";
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
    public static String TENANT_SETUP_PASSWORD = "http://localhost:8080/api/tenant/setup-password?token=%s&email=%s";
    public static String CLICK_THE_LINK_TO_SETUP_THE_PASSWORD = "<html><body><h2>Password Setup</h2><p>Click <a href='%s'>here</a> to set your password.</p></body></html>";
    public static String SET_YOUR_PASSWORD = "Set Your Password";
    public static String PASSWORD_SETUP_EMAIL_SENT_TO = "Password setup email sent to: {}";
    public static String RESENT_PASSWORD_SETUP_EMAIL_TO = "Resent password setup email to: {}";
    public static String TEACHER_NOT_FOUND_WITH_EMAIL = "Teacher not found with email: ";
    public static String TEACHER_RECORD_NOT_FOUND_FOR_EMAIL = "Teacher record not found for email: ";
    public static String TEACHER_UPDATED_SUCCESSFULLY = "Teacher updated successfully";
    public static String UNAUTHORIZED_ACCESS_TEACHER_DOES_NOT_BELONG_TO_YOUR_TENANT = "Unauthorized access: Teacher does not belong to your tenant";
    public static String TEACHER_DELETED_SUCCESSFULLY = "Teacher deleted successfully";
    public static String API_TEACHER_REGISTER_TEACHER_TOKEN = "%s/api/teacher/register/teacher?token=%s&email=%s";
    public static String HTTP_LOCALHOST_8080 = "http://localhost:8080";
    public static String EMAIL_ALREADY_REGISTERED = "Email already registered";
    public static String PROCESSING_TEACHER_REGISTRATION_FOR_EMAIL = "Processing teacher registration for email: {}";
    public static String ADMINTENANT_NOT_SET_OR_NOT_PERSISTED_IN_TOKEN = "AdminTenant not set or not persisted in token!";
    public static String ADMINTENANT_NOT_FOUND_IN_DB = "AdminTenant not found in DB";
    public static String TEACHER_REGISTERED_SUCCESSFULLY = "Teacher registered successfully: {}";
    public static String ERROR_DURING_TEACHER_REGISTRATION = "Error during teacher registration: {}";
    public static String UNAUTHORIZED_TEACHER_DOES_NOT_BELONG_TO_YOUR_TENANT = "Unauthorized: Teacher does not belong to your tenant";
    public static String UNSUPPORTED_PRINCIPAL_TYPE = "Unsupported principal type";
    public static String USER_PRINCIPAL_IS_NOT_AUTHENTICATED_PROPERLY = "User principal is not authenticated properly";
    public static String SUBJECT_NOT_FOUND_OR_NOT_OWNED_BY_THIS_TENANT = "Subject not found or not owned by this tenant.";
    public static String NO_MATCHING_CLASS_FOUND_FOR_THE_GIVEN_NAMES_UNDER_THIS_TENANT = "No matching class found for the given names under this tenant.";
    public static String NO_ACTIVE_SESSION_FOR_TENANT = "No active session for tenant";
    public static String CLASS_NOT_FOUND_FOR_THE_SESSION_AND_CLASSNAME = "Class_not_found_for_the_session_and_className";
    public static String A_STUDENT_WITH_THE_SAME_FULL_NAME_ALREADY_EXISTS_IN_THIS_SESSION = "A student with the same full name already exists in this session";
    public static final String BRANCH_NOT_FOUND = "Branch not found";
    public static String EMAIL_AND_PASSWORD_ARE_REQUIRED_TO_CREATE_STUDENT_LOGIN_ACCOUNT = "Email and password are required to create student login account";
    public static String A_USER_WITH_THIS_STUDENT_EMAIL_ALREADY_EXISTS = "A user with this student email already exists";
    public static String YOUR_STUDENT_CODE_NUMBER_IS = "Your student code number is => ";
    public static String STUDENT_REGISTERED_SUCCESSFULLY = "Student registered successfully";
    public static final String DUPLICATE_SESSION_FOR_THIS_SESSION = "Duplicate session for this tenant/branch";
    public static final String SESSION_CREATED_SUCCESSFULLY = "Session created successfully";
    public static final String SESSION_NOT_FOUND = "Session not found";
    public static final String BRANCH_DOES_NOT_BELONG_TO_YOUR_SCHOOL = "Branch does not belong to your school";
    public static final String START_DATE_MUST_BE_BEFORE_END_DATE = "Start date must be before end date";
    public static final String START_AND_END_DATES_ARE_REQUIRED = "Start and end dates are required";
    public static final String ONLY_ADMINS_CAN_UPDATE_INACTIVE_SESSIONS = "Only admins can update inactive sessions";
    public static final String CANNOT_UPDATE_SESSION_FROM_ANOTHER_TENANT = "Cannot update session from another tenant";
    public static final String YOU_CANNOT_RESTORE_A_SESSION_FROM_ANOTHER_TENANT = "You cannot restore a session from another tenant";
    public static final String SESSION_IS_ALREADY_ACTIVE = "Session is already active";
    public static final String CANNOT_DELETE_SESSION_WITH_ACADEMIC_RECORDS_ARCHIVE_INSTEAD = "Cannot delete session with academic records. Archive instead.";
    public static final String SESSION_IS_ALREADY_ARCHIVED = "Session is already archived";
    public static final String YOU_CANNOT_DELETE_A_SESSION_FROM_ANOTHER_TENANT = "You cannot delete a session from another tenant";

//    public static String A_STUDENT_WITH_THE_SAME_FULL_NAME_ALREADY_EXISTS_IN_THIS_SESSION = "A student with the same full name already exists in this session";
//    public static String A_STUDENT_WITH_THE_SAME_FULL_NAME_ALREADY_EXISTS_IN_THIS_SESSION = "A student with the same full name already exists in this session";
//    public static String A_STUDENT_WITH_THE_SAME_FULL_NAME_ALREADY_EXISTS_IN_THIS_SESSION = "A student with the same full name already exists in this session";
//

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