package ru.javaops.web;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javaops.config.AppProperties;
import ru.javaops.model.User;
import ru.javaops.model.UserGroup;
import ru.javaops.service.GroupService;
import ru.javaops.service.GroupService.ProjectGroups;
import ru.javaops.service.MailService;
import ru.javaops.service.SubscriptionService;
import ru.javaops.service.UserService;
import ru.javaops.to.UserTo;
import ru.javaops.util.Util;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.Date;

/**
 * GKislin
 */
@Controller
public class SubscriptionController {
    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public ModelAndView activate(@RequestParam("email") String email, @RequestParam("activate") boolean activate, @RequestParam("key") String key) {
        if (subscriptionService.isActivationKeyMatch(email, key)) {
            User u = userService.findExistedByEmail(email);
            if (u != null) {
                if (u.isActive() != activate) {
                    u.setActive(activate);
                    u.setActivatedDate(new Date());
                    userService.save(u);
                    return new ModelAndView("activation",
                            ImmutableMap.of("activate", activate,
                                    "subscriptionUrl", subscriptionService.getSubscriptionUrl(email, key, !activate)));
                }
            }
        }
        throw new IllegalArgumentException();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerByProject(@RequestParam("project") String project,
                                          @RequestParam(value = "template", required = false) String template,
                                          @RequestParam("channel") String channel,
                                          @Valid UserTo userTo, BindingResult result) throws MessagingException {
        if (result.hasErrors()) {
            throw new ValidationException(Util.getErrorMessage(result));
        }
        return register(template, appProperties.getTestEmail(), channel, "http://javawebinar.ru/confirm.html", "http://javawebinar.ru/error.html", userTo, result, groupService.getProjectGroups(project));
    }

    private ModelAndView register(String template, String confirmEmail, String channel, String successUrl, String failUrl,
                                  UserTo userTo, BindingResult result,
                                  ProjectGroups projectGroups) throws MessagingException {
        LOG.info("{} registration at {}", userTo, projectGroups.project);
        UserGroup userGroup = groupService.addToGroup(userTo, projectGroups, channel);
        String mailResult = mailService.sendRegistration(template, projectGroups.project, userGroup, confirmEmail);
        return new ModelAndView("redirectToUrl", "redirectUrl", MailService.isOk(mailResult) ? successUrl : failUrl);
    }
}