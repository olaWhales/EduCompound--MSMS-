package org.example.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

//package org.example.email;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//@AllArgsConstructor
//public class EmailService {
//    private final JavaMailSender mailSender ;
//    public void sendEmail(String to, String subject, String text) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(text, true);
//
//            mailSender.send(message);
//            log.info("Email sent successfully");
//        } catch (MessagingException e) {
//            log.error("Failed to send email");
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "EmailService{" +
//                "mailSender=" + mailSender +
//                '}';
//    }
//    @PostConstruct
//    public void verifyMailSetup() {
//        if (mailSender instanceof org.springframework.mail.javamail.JavaMailSenderImpl impl) {
//            System.out.println(">>> Mail Host: " + impl.getHost());
//            System.out.println(">>> Mail Username: " + impl.getUsername());
//            System.out.println(">>> Mail Port: " + impl.getPort());
//        } else {
//            System.out.println("MailSender is not an instance of JavaMailSenderImpl");
//        }
//    }
//}

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Async
    @EventListener
    public void handleEmailEvent(EmailEvent event) {
        log.info("Sending email to: {}, subject: {}", event.getTo(), event.getSubject());
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(event.getTo());
            helper.setSubject(event.getSubject());
            helper.setText(event.getContent(), event.isHtml());
            mailSender.send(mimeMessage);
            log.info("Email sent successfully to: {}", event.getTo());
        } catch (MessagingException e) {
            log.error("Failed to send email to: {}", event.getTo(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}