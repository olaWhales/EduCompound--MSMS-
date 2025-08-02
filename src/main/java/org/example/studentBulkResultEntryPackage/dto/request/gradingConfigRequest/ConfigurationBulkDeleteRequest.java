package org.example.studentBulkResultEntryPackage.dto.request.gradingConfigRequest;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigurationBulkDeleteRequest {
    private List<String> ids;
}
