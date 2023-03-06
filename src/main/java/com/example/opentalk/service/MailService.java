package com.example.opentalk.service;



import com.example.opentalk.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;


    @Async
    public void sendMailWelcome(String email, String content, String subject) throws Status {
        System.out.println("hfsdaffffffffffsdfsdfffffffffffvvv ----- " + Thread.currentThread().getName());
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(content);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            System.out.println("error" + e.getMessage());
            throw new Status(HttpStatus.INTERNAL_SERVER_ERROR, "không thể gửi mail");
        }
    }
}
