package org.example.subjectPackage.subjectService;

import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;

import java.util.UUID;

public interface ViewSingleSubjectService {
    SubjectResponseDTO getSubjectById(UUID subjectId);
}
