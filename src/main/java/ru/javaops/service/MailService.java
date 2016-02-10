package ru.javaops.service;

import com.fasterxml.jackson.annotation.JsonProperty;
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
import java.util.Collection;
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
    @Qualifier("mailExecutor")
    private Executor mailExecutor;

    public GroupResult sendToGroup(String template, String groupName) {
        return sendToUserList(template, userRepository.findByGroupName(groupName));
    }

    public GroupResult sendToProjectByGroupType(String template, String projectName, GroupType type) {
        return sendToUserList(template, userRepository.findByProjectAndGroupType(projectName, type));
    }

    public GroupResult sendToUserList(String template, Collection<User> users) {
        checkNotNull(template, "template must not be null");
        checkNotNull(users, "users must not be null");
        CompletionService<String> completionService = new ExecutorCompletionService<>(mailExecutor);
        List<Future<String>> resultList =
                users.stream()
                        .map(u -> completionService.submit(() -> sendToUser(template, u)))
                        .collect(Collectors.toList());

        final GroupResultBuilder groupResultBuilder = new GroupResultBuilder();
        try {
            while (!resultList.isEmpty()) {
                Future<String> future = completionService.poll(10, TimeUnit.SECONDS);
                if (future == null) {
                    cancelAll(resultList);
                    return groupResultBuilder.buildWithFailure("+++ Interrupted by timeout");
                } else {
                    if (!groupResultBuilder.accept(future)) {
                        cancelAll(resultList);
                        return groupResultBuilder.buildWithFailure("+++ Interrupted by faults number");
                    }
                    resultList.remove(future);
                }
            }
        } catch (InterruptedException e) {
            return groupResultBuilder.buildWithFailure("+++ Interrupted");
        }
        return groupResultBuilder.build();
    }

    private void cancelAll(List<Future<String>> resultList) {
        resultList.forEach(f -> f.cancel(true));
    }

    @Async("mailExecutor")
    public Future<String> sendToUserAsync(String template, String email) {
        return new AsyncResult<>(sendToUser(template, email));
    }

    public String sendToUser(String template, String email) {
        checkNotNull(template, "Template must not be null");
        checkNotNull(email, "email  must not be null");
        return sendToUser(template, userRepository.findByEmail(email.toLowerCase()));
    }

    public String sendToUser(String template, User user) {
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
            sendToUser(user.getEmail(), user.getFirstName(), subject, content, false, true, subscriptionUrl);
            result = OK;
        } catch (MessagingException | MailException e) {
            result = e.getMessage();
            LOG.error("Sending to {} failed: \n{}", user.getEmail(), result);
        }
        mailCaseRepository.save(new MailCase(user, subject, result));
        return result;
    }

    public void sendToUser(String toEmail, String toName, String subject, String content, boolean isMultipart, boolean isHtml, String subscriptionUrl) throws MessagingException {
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

    public static class GroupResultBuilder {
        int success = 0;
        int failed = 0;
        final StringBuilder failedCauses = new StringBuilder();

        private GroupResult build() {
            return new GroupResult(success, failed, failedCauses.length() == 0 ? null : failedCauses.toString());
        }

        private GroupResult buildWithFailure(String cause) {
            failedCauses.append('\n').append(cause);
            return build();
        }

        boolean accept(Future<String> result) {
            try {
                if (OK.equals(result.get())) {
                    success++;
                } else {
                    failed++;
                    failedCauses.append(result).append('\n');
                }
            } catch (InterruptedException e) {
                failed++;
                failedCauses.append("\nTask interrupted");
            } catch (ExecutionException e) {
                failed++;
                failedCauses.append(e.getCause()).append('\n');
            }
            return failed < success || failed < 5;
        }
    }

    public static class GroupResult {
        final int failed;
        final int success;
        final String failedCauses;

        public GroupResult(@JsonProperty("success") int success, @JsonProperty("failed") int failed, @JsonProperty("failedCauses") String failedCauses) {
            this.success = success;
            this.failed = failed;
            this.failedCauses = failedCauses;
        }

        @Override
        public String toString() {
            return String.format("Success: %d\nFailed: %d\nReasons:%s", success, failed, failedCauses);
        }

        public boolean isOk() {
            return failedCauses == null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GroupResult that = (GroupResult) o;

            if (failed != that.failed) return false;
            if (success != that.success) return false;
            return failedCauses != null ? failedCauses.equals(that.failedCauses) : that.failedCauses == null;

        }

        @Override
        public int hashCode() {
            int result = failed;
            result = 31 * result + success;
            result = 31 * result + (failedCauses != null ? failedCauses.hashCode() : 0);
            return result;
        }
    }
}
