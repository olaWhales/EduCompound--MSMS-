package org.example.subjectPackage.subjectService;

import org.example.data.model.ClassRoom;
import org.example.data.model.Subject;
import org.example.subjectPackage.dto.subjectResponse.SubjectResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubjectMapper {

    public SubjectResponseDTO toResponse(Subject subject) {
        List<String> classNames = subject.getAssignedClasses().stream()
                .map(ClassRoom::getClassName)
                .collect(Collectors.toList());

        return SubjectResponseDTO.builder()
                .name(subject.getName())
                .description(subject.getDescription())
                .classNames(classNames)
                .id(subject.getId())
                .build();
    }
}
