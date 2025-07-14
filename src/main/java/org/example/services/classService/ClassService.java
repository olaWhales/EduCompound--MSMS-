package org.example.services.classService;

import org.example.dto.requests.ClassRequest;
import org.example.dto.responses.ClassResponse;

public interface ClassService {
    ClassResponse classResponse(ClassRequest classRequest);
}
