package ru.javaops.web;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.javaops.model.User;
import ru.javaops.repository.UserRepository;
import ru.javaops.service.MailService;
import ru.javaops.service.SubscriptionService;

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
    private UserRepository userRepository;

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public ModelAndView activate(@RequestParam("email") String email, @RequestParam("activate") boolean activate, @RequestParam("key") String key) {
        if (subscriptionService.isActivationKeyMatch(email, key)) {
            User u = userRepository.findByEmail(email);
            if (u != null) {
                if (u.isActive() != activate) {
                    u.setActive(activate);
                    u.setActivatedDate(new Date());
                    userRepository.save(u);
                    return new ModelAndView("activation",
                            ImmutableMap.of("activate", activate,
                                    "subscriptionUrl", subscriptionService.getSubscriptionUrl(email, key, !activate)));
                }
            }
        }
        throw new IllegalArgumentException();
    }

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    @ResponseBody
    public String activate(@RequestParam("email") String email) {
        LOG.info(email + " registration");
        return mailService.sendToUser("test", email);
    }
}