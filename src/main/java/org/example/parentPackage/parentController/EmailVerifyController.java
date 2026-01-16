package org.example.parentPackage.parentController;

import lombok.RequiredArgsConstructor;
import org.example.parentPackage.dto.parentRequest.VerificationRequestDto;
import org.example.parentPackage.dto.parentResponse.ParentRegisterResponse;
import org.example.tokenPackage.TokenService;
import org.example.parentPackage.parentService.ParentVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/parent")
public class EmailVerifyController {

    private final ParentVerificationService verificationService;
    private final TokenService tokenService;

    // This renders the Thymeleaf verification page
    @GetMapping("/verify-page")
    public String showVerificationPage(
            @RequestParam String email,
            @RequestParam String token, // token optional now
            Model model) {

        VerificationRequestDto request = new VerificationRequestDto();
        request.setEmail(email);
//        if (token != null) request.setToken(token);
        request.setToken(token);
        model.addAttribute("verificationRequest", request);
        return "verify";
    }

    // This handles form submission from the Thymeleaf page
//    @PostMapping("/verify")
//    public String verifyParent(@ModelAttribute("verificationRequest") VerificationRequestDto request,
//                               Model model) {
//        try {
//            if (!request.getPassword().equals(request.getVerifyPassword())) {
//                model.addAttribute("error", "Passwords do not match");
//                return "verify";
//            }
//
//            tokenService.validateToken(request.getEmail(), request.getToken());
//            tokenService.markEmailAsVerified(request.getEmail());
//
//            model.addAttribute("success", "Account activated successfully!");
//            return "verify-success";  // Your Thymeleaf success page
//
//        } catch (Exception ex) {
//            model.addAttribute("error", ex.getMessage());
//            model.addAttribute("verificationRequest", request);
//            return "verify";
//        }
//    }
    @PostMapping("/verify")
    public String verifyParent(
            @ModelAttribute("verificationRequest") VerificationRequestDto request,
            BindingResult result,
            Model model) {

        try {
            // Check if passwords match
            if (!request.getPassword().equals(request.getVerifyPassword())) {
                model.addAttribute("errorMessage", "Passwords do not match");
                return "verification-failed, password not matched";
            }

            // Validate token
            tokenService.validateToken(request.getEmail(), request.getToken());

            // Mark token as verified
            tokenService.markEmailAsVerified(request.getEmail());

            // Optional: Save password securely via verificationService
            verificationService.verifyToken(request.getEmail(), request.getToken(), request.getPassword());

            // Return Thymeleaf success page
            return "verification-success";

        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("verificationRequest", request); // re-inject form data
            return "verification-failed";
        }
    }


    // Optional: JSON endpoints if needed
    @GetMapping("/verify-success-json")
    @ResponseBody
    public ParentRegisterResponse verifySuccessJson() {
        return ParentRegisterResponse.builder()
                .message("Account activated successfully!")
                .createdAt(new Date())
                .build();
    }

    @GetMapping("/verify-error-json")
    @ResponseBody
    public ParentRegisterResponse verifyErrorJson() {
        return ParentRegisterResponse.builder()
                .message("Verification failed or token expired.")
                .createdAt(new Date())
                .build();
    }
}
