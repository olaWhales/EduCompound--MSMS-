package org.example.subjectPackage.subjectService;

import org.example.subjectPackage.dto.SubjectRequest.UpdateSubjectRequestDTO;
import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;

import java.util.UUID;

public interface UpdateSubjectService {
    SubjectResponseDTO updateSubject(UUID subjectId, UpdateSubjectRequestDTO request);

}
