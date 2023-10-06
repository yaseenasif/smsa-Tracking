package com.example.CargoTracking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String sender;

    public void sendEmail(String to, String subject) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(sender);
            helper.setText("<html>\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Shipment Notification</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h1>Shipment Notification</h1>\n" +
                    "    <p>The shipment is on its way to your destination.</p>\n" +
                    "</body>\n" +
                    "</html>", true);
            javaMailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("Error while sending mail");
        }

    }

}
