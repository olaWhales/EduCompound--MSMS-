package org.example.parentPackage.parentService;

import java.util.UUID;

public interface ParentAdminService {
    void activateParent(UUID userId);
    void deactivateParent(UUID userId);
}
