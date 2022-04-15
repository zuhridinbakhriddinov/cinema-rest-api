package uz.pdp.appcinemarest.mail;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import uz.pdp.appcinemarest.entity.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

// Zuhridin Bakhriddinov 4/4/2022 3:03 PM
@Service
public class MailService {

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    private FreeMarkerConfigurer freemarkerConfigurer;


    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);

        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }


    public void sendMessageUsingFreemarkerTemplate(
            Map<String, Object> templateModel)
            throws IOException, TemplateException, MessagingException {

        Template freemarkerTemplate = freemarkerConfigurer.getConfiguration()
                .getTemplate("template-freemarker.ftl");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        sendHtmlMessage("zukicgleo@gmail.com", "text", htmlBody);
    }

}
