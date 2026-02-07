package org.example.sessionPackage.sessionService.cycleValidation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class TimeConfiguration {

    @Bean
    public Clock systemClock() {
        return Clock.system(ZoneId.systemDefault());
    }
}
