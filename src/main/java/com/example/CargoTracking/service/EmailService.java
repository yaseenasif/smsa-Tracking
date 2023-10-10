package com.example.CargoTracking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

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

            Resource htmlResource = new ClassPathResource("email.html");
            String htmlContent = StreamUtils.copyToString(htmlResource.getInputStream(), StandardCharsets.UTF_8);

            Resource cssResource = new ClassPathResource("email.css");
            String cssContent = StreamUtils.copyToString(cssResource.getInputStream(), StandardCharsets.UTF_8);

//            String styledHTMLContent = "<style>" + cssContent + "</style>" + htmlContent;
//            helper.setText(styledHTMLContent, true);

            helper.setText(htmlContent, true);
            javaMailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("Error while sending mail");
        }

    }

}
