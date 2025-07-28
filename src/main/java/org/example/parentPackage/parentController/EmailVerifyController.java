//package org.example.controller.parentController;
//
//import lombok.RequiredArgsConstructor;
//import org.example.dto.requests.parentRequest.VerificationRequestDto;
//import org.example.tokenPackages.TokenService;
//import org.example.services.parentService.ParentVerificationService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/parent")
//public class EmailVerifyController {
//
//    private final ParentVerificationService verificationService;
//    private final TokenService tokenService ;
//
//    @GetMapping("/verify-page")
////    public String showVerificationPage(Model model, @RequestParam(value = "email", required = false) String email) {
//    public String showVerificationPage(@RequestParam String email, Model model) {
//        VerificationRequestDto request = new VerificationRequestDto();
////        if (email != null) request.setEmail(email);
//        request.setEmail(email);
//        model.addAttribute("verificationRequest", request);
//        return "verify";
//    }
//
//    @PostMapping("/verify")
//    public String verifyParent(
//            @ModelAttribute("verificationRequest") VerificationRequestDto request,
//            BindingResult result,
//            Model model) {
//        try {
//            // check if passwords match
//            if (!request.getPassword().equals(request.getVerifyPassword())) {
//                model.addAttribute("error", "Passwords do not match");
//                return "verify";
//            }
//
//            // Validate token and update password
//            tokenService.validateToken(request.getEmail(), request.getToken());
//            tokenService.markEmailAsVerified(request.getEmail(), request.getToken());
//            // Save password logic...
//
//            model.addAttribute("success", "Account activated successfully!");
//            return "verify-success";  // redirect or success UI
//        } catch (Exception ex) {
//            model.addAttribute("error", ex.getMessage());
//            model.addAttribute("verificationRequest", request);  // Reinject form object
//            return "verify";
//        }
//    }
//
//}
package org.example.parentPackage.parentController;

import lombok.RequiredArgsConstructor;
import org.example.parentPackage.dto.parentRequest.VerificationRequestDto;
import org.example.parentPackage.dto.parentResponse.ParentRegisterResponse;
import org.example.tokenPackage.TokenService;
import org.example.parentPackage.parentService.ParentVerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parent")
public class EmailVerifyController {

    private final ParentVerificationService verificationService;
    private final TokenService tokenService;

    @PostMapping("/verify")
    public ResponseEntity<ParentRegisterResponse> verifyParent(@RequestBody VerificationRequestDto request) {
        try {
            // Check if passwords match
            if (!request.getPassword().equals(request.getVerifyPassword())) {
                return ResponseEntity.badRequest().body(
                        ParentRegisterResponse.builder()
                            .message("Passwords do not match.")
                            .createdAt(new Date())
                            .build()
                );
            }

            // Validate token
            tokenService.validateToken(request.getEmail(), request.getToken());

            // Mark token as verified
            tokenService.markEmailAsVerified(request.getEmail());

            // Optional: Save the password securely via verificationService (not shown here)

            // Return success response
            return ResponseEntity.ok(
                    ParentRegisterResponse.builder()
                            .message("Account activated successfully!")
                            .createdAt(new Date())
                            .build()
            );

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ParentRegisterResponse.builder()
                            .message("Verification failed: " + ex.getMessage())
                            .createdAt(new Date())
                            .build()
            );
        }
    }

    // Optional test endpoint to confirm success message
    @GetMapping("/verify-success-json")
    public ParentRegisterResponse verifySuccessJson() {
        return ParentRegisterResponse.builder()
                .message("Account activated successfully!")
                .createdAt(new Date())
                .build();
    }

    // Optional test endpoint to simulate a verification failure
    @GetMapping("/verify-error-json")
    public ParentRegisterResponse verifyErrorJson() {
        return ParentRegisterResponse.builder()
                .message("Verification failed or token expired.")
                .createdAt(new Date())
                .build();
    }
}
