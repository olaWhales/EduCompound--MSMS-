package org.example.subjectPackage.subjectService;

import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;

import java.util.UUID;

public interface SubjectService {
    SubjectResponseDTO getSubjectById(UUID subjectId);
}
