package org.example.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender ;
    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            mailSender.send(message);
            log.info("Email sent successfully");
        } catch (MessagingException e) {
            log.error("Failed to send email");
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
