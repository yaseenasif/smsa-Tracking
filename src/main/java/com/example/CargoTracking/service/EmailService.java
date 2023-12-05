package com.example.CargoTracking.service;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    Configuration config;


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
            e.printStackTrace();
            throw new RuntimeException("Error while sending mail"+e.getMessage());
        }

    }


    public void sendHtmlEmail(String to,String subject,String emailTemplate,Map<String,Object> model) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(sender);
//            EmailTemplate emailTemplate = emailTemplateRepository.findById(3L).get();
//            String htmlContent = emailTemplate.getContent();
//            String replacedContent = htmlContent.toString().replace("{name}", "JOHN DEO");
//            helper.setText(replacedContent, true);
//            Map<String, Object> model = new HashMap<>();
//            model.put("name", "John Doe");

            Template t = config.getTemplate(emailTemplate);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setText(html, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException | TemplateException e) {
            // Handle exceptions
        }
    }



}
