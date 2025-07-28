package org.example.classPackage.classService;

import org.example.classPackage.dto.classRoomRequest.ClassRequest;
import org.example.classPackage.dto.classRoomResponse.ClassResponse;

public interface ClassService {
    ClassResponse classResponse(ClassRequest classRequest);
}
