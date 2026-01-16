package org.example.parentPackage.parentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.data.model.*;
import org.example.data.repositories.*;
import org.example.parentPackage.dto.parentRequest.ParentRegisterRequest;
import org.example.parentPackage.dto.parentResponse.ParentRegisterResponse;
import org.example.email.EmailEvent;
import org.example.exceptions.EmailDeliveryException;
import org.example.exceptions.ParentAlreadyRegisteredException;
import org.example.tokenPackage.TokenService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@RequiredArgsConstructor
public class ParentRegisterServiceImpl implements ParentRegisterService {

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final ApplicationEventPublisher eventPublisher;

    // In-memory temporary storage for registration requests
    private final ConcurrentMap<String, ParentRegisterRequest> tempStorage = new ConcurrentHashMap<>();

    @Override
    @Transactional
    public ParentRegisterResponse registerParent(ParentRegisterRequest request) {
        String email = request.getEmail();

        // 1. Check if parent with email already exists
//        if (userRepository.existsByEmail(email)) {throw new ParentAlreadyRegisteredException("Parent already registered with this email, please link the student with this parent details.");}
        Optional<Users> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            Users user = existingUser.get();

            if (user.isVerified()) {throw new ParentAlreadyRegisteredException("Parent already registered with this email.");}
            // 👇 User exists but not verified → resend verification
            resendVerification(user);

            return ParentRegisterResponse.builder()
                    .parentId(user.getUserId())
                    .message("Account exists but not verified. Verification email resent.")
                    .createdAt(new Date())
                    .build();
        }

        // 2. Get current tenant from authenticated user
        AdminTenant tenant = extractTenantFromAuth();


        // 3. Create user as inactive
        Users parentUser = Users.builder()
                .email(email)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .active(false) // inactive until verification
                .verified(false)   // ✅ ADD THIS
                .role(Role.PARENT)
                .adminTenant(tenant)
                .statusUpdatedAt(Instant.now())
                .build();
        userRepository.save(parentUser);  // ✅ save the user here

        // 3. Generate 6-digit token
        String tokenValue = tokenService.generateSixDigitToken();
        tempStorage.put(tokenValue, request); // Temporarily store registration

        // 4. Build and send email/SMS
        sendVerificationCommunication(request, tokenValue);

        // 5. Save token to database
        saveVerificationToken(email, tenant, tokenValue);

        return ParentRegisterResponse.builder()
                .parentId(parentUser.getUserId())  // ✅ ADD
                .message("Verification email sent successfully. Check your inbox.")
                .createdAt(new Date())
                .build();
    }

    private void sendVerificationCommunication(ParentRegisterRequest request, String token) {
        String email = request.getEmail();
        String firstName = request.getFirstName();
        String link = "http://localhost:8080/parent/verify-page?email=" + email + "&token=" + token;

        String emailBody = buildEmailBody(firstName, token, link);
        String smsBody = "Dear " + firstName + ", your verification code is: " + token +
                ". Visit " + link + " to activate your account. Expires in 15 mins.";
        try {
            eventPublisher.publishEvent(new EmailEvent(this, email, "Activate Your Parent Account", emailBody, true));
//            eventPublisher.publishEvent(new EmailEvent(this, request.getPhone(), "Parent Account Activation", smsBody, true));
        } catch (Exception e) {
            tempStorage.remove(token);
            throw new EmailDeliveryException("Registration failed: Could not send verification email.");
        }
    }

    private void saveVerificationToken(String email, AdminTenant tenant, String tokenValue) {
        Token token = Token.builder()
                .token(tokenValue)
                .email(email)
                .adminTenant(tenant)
                .createdAt(new Date())
                .expiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 mins
                .verified(false)
                .build();
        tokenRepository.save(token);
    }

    private void resendVerification(Users user) {
        String tokenValue = tokenService.generateSixDigitToken();

        saveVerificationToken(user.getEmail(), user.getAdminTenant(), tokenValue);

        ParentRegisterRequest fakeRequest = ParentRegisterRequest.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .phone(user.getPhone())
                .build();

        sendVerificationCommunication(fakeRequest, tokenValue);
    }


    private String buildEmailBody(String firstName, String token, String link) {
            return "<html><body>" +
                    "<p>Dear " + firstName + ",</p>" +
                    "<p>You are about to be registered as a parent.</p>" +
//                    "<p>Your verification code is:</p>" +
//                    "<p style=\"text-align: center; font-weight: bold;\">" + token + "</p>" +
                    "<p>Please click the button below to activate your account and set your password:</p>" +
                    "<p style=\"text-align: center;\">" +
                    "<a href=\"" + link + "\" " +
                    "style=\"background-color:#28a745;color:white;padding:12px 20px;text-decoration:none;border-radius:5px;font-weight:bold;\">" +
                    "Activate Account</a>" +
                    "</p>" +
                    "<p>This link will expire in 15 minutes.</p>" +
                    "</body></html>";
            //                "<html><body>" +
//                "<p>Dear " + firstName + ",</p>" +
//                "<p>You have been registered as a parent.</p>" +
//                "<p>Your verification code is:</p>" +
//                "<p style=\"text-align: center; font-weight: bold;\">&nbsp;&nbsp;&nbsp;&nbsp;" + token + "</p>" +
//                "<p>Please visit the link below to activate your account and set your password:</p>" +
//                "<p><a href=\"" + link + "\">" + link + "</a></p>" +
//                "<p>This link will expire in 15 minutes.</p>" +
//                "</body></html>";
    }

    // ✅ Helper method to extract tenant from authenticated admin user
    private AdminTenant extractTenantFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {throw new IllegalStateException("No authenticated user found.");}
        UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
        Users adminUser = principal.users();

        AdminTenant tenant = adminUser.getAdminTenant();
        if (tenant == null) {
            throw new IllegalStateException("Admin user has no associated tenant.");
        }

        return tenant;
    }
}
