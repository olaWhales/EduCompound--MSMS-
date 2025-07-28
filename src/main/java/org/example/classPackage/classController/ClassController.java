package org.example.classPackage.classController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.classPackage.dto.classRoomRequest.ClassRequest;
import org.example.classPackage.dto.classRoomResponse.ClassResponse;
import org.example.classPackage.classService.ClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    @PostMapping("/classes/create")
    public ResponseEntity<ClassResponse> createClass(@Valid @RequestBody ClassRequest request) {
        ClassResponse response = classService.classResponse(request);
        return ResponseEntity.ok(response);
    }
}
