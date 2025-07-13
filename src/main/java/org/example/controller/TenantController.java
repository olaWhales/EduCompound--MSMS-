package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.dto.requests.PasswordSetupRequest;
import org.example.service.tenantService.PasswordSetupService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/tenant")
@AllArgsConstructor
public class TenantController {
    private final PasswordSetupService passwordSetupService;

    @GetMapping("/setup-password")
    public String showPasswordSetupForm(@RequestParam String token, @RequestParam String email, Model model) {
        PasswordSetupRequest passwordSetupRequest = new PasswordSetupRequest();
        passwordSetupRequest.setEmail(email); // Pre-set email
        model.addAttribute("passwordSetupRequest", passwordSetupRequest);
        model.addAttribute("token", token); // For potential validation
        return "password-setup";
    }
    @PostMapping("/setup-password")
    public ResponseEntity<String> setPassword(@Valid @ModelAttribute PasswordSetupRequest request, BindingResult bindingResult, @RequestParam(required = false) String token) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Password is required.");
        }
        try {
            passwordSetupService.setPassword(request, token); // Pass token for validation
            return ResponseEntity.ok("Password set successfully. Please login.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}