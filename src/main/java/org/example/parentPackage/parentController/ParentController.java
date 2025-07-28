package org.example.parentPackage.parentController;

import lombok.RequiredArgsConstructor;
import org.example.parentPackage.dto.parentRequest.ParentRegisterRequest;
import org.example.parentPackage.parentService.ParentRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/parent")
@RequiredArgsConstructor
public class ParentController {

    private final ParentRegisterService parentRegisterService;

    @PostMapping("/register")
    @ResponseBody
    public String registerParent(@ModelAttribute ParentRegisterRequest request) {
        try {
            parentRegisterService.registerParent(request);
            return "Parent registered successfully. Check email for activation link.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}