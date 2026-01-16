package org.example.acountStatusPolicy;

import java.util.UUID;

public interface AccountActivationService {
    void changeStatus(UUID targetUserId, boolean active);
}
