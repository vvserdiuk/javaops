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
import ru.javaops.model.Channel;
import ru.javaops.model.User;
import ru.javaops.model.UserGroup;
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
    private SubscriptionService subscriptionService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public ModelAndView activate(@RequestParam("email") String email, @RequestParam("activate") boolean activate, @RequestParam("key") String key) {
        if (subscriptionService.isActivationKeyMatch(email, key)) {
            User u = userService.findByEmail(email);
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
    public ModelAndView activate(@RequestParam("template") String template,
                                 @RequestParam(value = "group", required = false) String group,
                                 @RequestParam("confirm_email") String confirmEmail,
                                 @RequestParam("channel") Channel channel,
                                 @RequestParam("success_url") String successUrl,
                                 @RequestParam("fail_url") String failUrl,
                                 @Valid UserTo userTo, BindingResult result) throws MessagingException {

        if (result.hasErrors()) {
            throw new ValidationException(Util.getErrorMessage(result));
        }
        if (group == null) {
            group = template;
        }
        LOG.info(userTo + " registration at " + group);
        UserGroup userGroup = userService.addToGroup(userTo, group, channel);
        String mailResult = mailService.sendRegistration(template, userGroup, confirmEmail);
        return new ModelAndView("redirectToUrl", "redirectUrl", MailService.isOk(mailResult) ? successUrl : failUrl);
    }
}