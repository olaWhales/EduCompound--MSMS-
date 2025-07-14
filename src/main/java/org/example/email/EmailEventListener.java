package org.example.email;//package org.example.email;
//
//import org.example.email.EmailEvent;
//import org.example.email.EmailService;
//import org.springframework.context.event.EventListener;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailEventListener {
//    private final EmailService emailService;
//
//    public EmailEventListener(EmailService emailService) {
//        this.emailService = emailService;
//    }
//
//    @Async
//    @EventListener
//    public void handleEmailEvent(EmailEvent event) {
//        emailService.sendEmail(event.getTo(), event.getSubject(), event.getText());
//    }
//
//    @Override
//    public String toString() {
//        return "EmailEventListener{" +
//                "emailService=" + emailService +
//                '}';
//    }
//}

//
//package org.example.email;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.event.EventListener;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EmailEventListener {
//
//    private final EmailService emailService;
//
//    @Async
//    @EventListener
//    public void handleEmailEvent(EmailEvent event) {
//        emailService.sendEmail(
//                event.getTo(),
//                event.getSubject(),
//                event.getContent(),
//                event.isHtml()
//        );
//    }
//}



import lombok.AllArgsConstructor;
import org.example.email.EmailEvent;
import org.example.email.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailEventListener {
    private final EmailService emailService;

    public EmailEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    @EventListener
    public void handleEmailEvent(EmailEvent event) {
        emailService.sendEmail(event.getTo(), event.getSubject(), event.getText());
    }

    @Override
    public String toString() {
        return "EmailEventListener{" +
                "emailService=" + emailService +
                '}';
    }
}
