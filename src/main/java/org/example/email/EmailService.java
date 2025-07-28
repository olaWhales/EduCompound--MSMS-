package org.example.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
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
