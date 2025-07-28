package org.example.subjectPackage.subjectService;

import org.example.subjectPackage.dto.SubjectRequest.SubjectRequestDTO;
import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;

public interface CreateSubjectService {
    SubjectResponseDTO createSubject(SubjectRequestDTO request);

}
