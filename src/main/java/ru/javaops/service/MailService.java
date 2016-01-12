package ru.javaops.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import ru.javaops.model.GroupType;
import ru.javaops.model.MailCase;
import ru.javaops.model.User;
import ru.javaops.repository.MailCaseRepository;
import ru.javaops.repository.UserRepository;
import ru.javaops.util.MailUtil;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class MailService {

    public static final String OK = "OK";
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCaseRepository mailCaseRepository;

    @Autowired
    private UserRepository userRepository;

    private final Logger LOG = LoggerFactory.getLogger(MailService.class);

    public String sendGroup(String template, String projectName, GroupType type) {
        List<User> users = userRepository.findByProjectAndGroupType(projectName, type);
        int failed = 0;
        int success = 0;
        StringBuilder failedCause = new StringBuilder();
        for (User u : users) {
            String result = sendEmail(template, u);
            if (result.equals(OK)) {
                success++;
            } else {
                failed++;
                failedCause.append(result).append('\n');
            }
            if (failed > success && failed > 5) {
                failedCause.append("\nInterrupted");
            }
        }
        return String.format("Success: %d\nFailed: %d\nReasons:%s", success, failed, failedCause.toString());
    }

    public String sendEmail(String template, String email) {
        Assert.notNull(template, "template must not be null");
        Assert.notNull(email, "email  must not be null");
        return sendEmail(template, userRepository.findByEmail(email.toLowerCase()));
    }

    public String sendEmail(String template, User user) {
        LOG.debug("Sending {} email to '{}'", template, user.getEmail());
        Context context = new Context(
                Locale.forLanguageTag("ru"),
                new HashMap<String, Object>() {{
                    put("user", user);
                }});
        String content = templateEngine.process(template, context);
        final String subject = MailUtil.getTitle(content);
        String result;
        try {
            sendEmail(user.getEmail(), subject, content, false, true);
            result = OK;
        } catch (MessagingException e) {
            result = e.toString();
            LOG.error("Sending to {} failed: \n{}", user.getEmail(), result);
        }
        mailCaseRepository.save(new MailCase(user, subject, result));
        return result;
    }

    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) throws MessagingException {
        LOG.debug("Send email [multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, "UTF-8");
        message.setTo(to);
        message.setFrom("javawebinar@yandex.ru");
        message.setSubject(subject);
        message.setText(content, isHtml);
        javaMailSender.send(mimeMessage);
    }
}
