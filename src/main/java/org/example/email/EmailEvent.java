package org.example.email;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailEvent extends ApplicationEvent {
    private final String to;
    private final String subject;
    private final String text;

    public EmailEvent(Object source, String to, String subject, String text) {
        super(source);
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    @Override
    public String toString() {
        return "EmailEvent{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", source=" + source +
                '}';
    }
}
