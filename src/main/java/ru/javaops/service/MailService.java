package ru.javaops.service;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import ru.javaops.config.AppProperties;
import ru.javaops.model.GroupType;
import ru.javaops.model.MailCase;
import ru.javaops.model.User;
import ru.javaops.repository.MailCaseRepository;
import ru.javaops.repository.UserRepository;
import ru.javaops.util.MailUtil;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class MailService {
    public static final Locale LOCALE_RU = Locale.forLanguageTag("ru");
    public static final String OK = "OK";
    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCaseRepository mailCaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private AppProperties appProperties;

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
        String activationKey = subscriptionService.generateActivationKey(user.getEmail());
        String subscriptionUrl = subscriptionService.getSubscriptionUrl(user.getEmail(), activationKey, false);
        Context context = new Context(LOCALE_RU,
                ImmutableMap.of("user", user, "subscriptionUrl", subscriptionUrl));
        String content = templateEngine.process(template, context);
        final String subject = MailUtil.getTitle(content);
        String result;
        try {
            sendEmail(user.getEmail(), user.getFirstName(), subject, content, false, true, subscriptionUrl);
            result = OK;
        } catch (MessagingException | MailException e) {
            result = e.getMessage();
            LOG.error("Sending to {} failed: \n{}", user.getEmail(), result);
        }
        mailCaseRepository.save(new MailCase(user, subject, result));
        return result;
    }

    public void sendEmail(String toEmail, String toName, String subject, String content, boolean isMultipart, boolean isHtml, String subscriptionUrl) throws MessagingException {
        LOG.debug("Send email to '{} <{}>' with subject '{}'", toName, toEmail, subject);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        if (subscriptionUrl != null) {
            mimeMessage.setHeader("List-Unsubscribe", '<' + subscriptionUrl + '>');
        }
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, "UTF-8");
        try {
            message.setTo(new InternetAddress(toEmail, toName, "UTF-8"));
            message.setFrom("admin@javaops.ru", "Java Online Projects");
        } catch (UnsupportedEncodingException e) { // dummy
        }
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
