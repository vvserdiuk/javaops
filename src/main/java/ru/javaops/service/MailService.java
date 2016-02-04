package ru.javaops.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
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
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class MailService {
    private final Logger LOG = LoggerFactory.getLogger(MailService.class);
    public static final String OK = "OK";

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCaseRepository mailCaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("mailExecutor")
    private Executor mailExecutor;

    public GroupResult sendGroup(String template, String projectName, GroupType type) {
        checkNotNull(template, "Template must not be null");
        List<User> users = userRepository.findByProjectAndGroupType(projectName, type);
        CompletionService<String> completionService = new ExecutorCompletionService<>(mailExecutor);
        List<Future<String>> resultList =
                users.stream()
                        .map(u -> completionService.submit(() -> sendEmail(template, u)))
                        .collect(Collectors.toList());

        final GroupResult groupResult = new GroupResult();
        try {
            while (!resultList.isEmpty()) {
                Future<String> future = completionService.poll(10, TimeUnit.SECONDS);
                if (future == null) {
                    cancelAll(resultList);
                    return groupResult.addFailCause("+++ Interrupted by timeout");
                } else {
                    if (!groupResult.accept(future)) {
                        cancelAll(resultList);
                        return groupResult.addFailCause("+++ Interrupted by faults number");
                    }
                    resultList.remove(future);
                }
            }
        } catch (InterruptedException e) {
            groupResult.addFailCause("+++ Interrupted");
        }
        return groupResult;
    }

    private void cancelAll(List<Future<String>> resultList) {
        resultList.forEach(f -> f.cancel(true));
    }

    @Async("mailExecutor")
    public Future<String> sendEmailAsync(String template, String email) {
        return new AsyncResult<>(sendEmail(template, email));
    }

    public String sendEmail(String template, String email) {
        checkNotNull(template, "Template must not be null");
        checkNotNull(email, "email  must not be null");
        return sendEmail(template, userRepository.findByEmail(email.toLowerCase()));
    }

    public String sendEmail(String template, User user) {
        checkNotNull(user, "User must not be null");
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

    public static class GroupResult {
        int failed = 0;
        int success = 0;
        final StringBuilder failedCause = new StringBuilder();

        @Override
        public String toString() {
            return String.format("Success: %d\nFailed: %d\nReasons:%s", success, failed, failedCause.toString());
        }

        public boolean isOk() {
            return failedCause.length() == 0;
        }

        private GroupResult addFailCause(String cause) {
            failedCause.append('\n').append(cause);
            return this;
        }

        private boolean accept(Future<String> result) {
            try {
                if (OK.equals(result.get())) {
                    success++;
                } else {
                    failed++;
                    failedCause.append(result).append('\n');
                }
            } catch (InterruptedException e) {
                failed++;
                failedCause.append("\nTask interrupted");
            } catch (ExecutionException e) {
                failed++;
                failedCause.append(e.getCause()).append('\n');
            }
            return failed < success || failed < 5;
        }
    }
}
