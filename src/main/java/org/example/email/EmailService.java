package org.example.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@Service
@AllArgsConstructor
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

//    @Async

    public void sendEmail(String to, String subject, String text) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(text, true);

                mailSender.send(message);
                System.out.println("Email sent successfully");
            } catch (MessagingException e) {
                System.out.println("Failed to send email");
                throw new RuntimeException(e);
            }
        }

        @Override
        public String toString() {
            return "EmailService{" +
                    "mailSender=" + mailSender +
                    '}';
        }
    }
