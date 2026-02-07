package org.example.sessionPackage.sessionService.cycleValidation;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TermLifecycleScheduler {

    private final TermLifecycleService lifecycleService;

    // Runs every day at 00:00
    @Scheduled(cron = "0 0 0 * * ?")
    public void runDailySessionLifecycle() {
        lifecycleService.synchronizeSessionStatuses();
    }
}
