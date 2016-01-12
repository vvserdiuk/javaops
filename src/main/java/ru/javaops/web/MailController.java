package ru.javaops.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.model.GroupType;
import ru.javaops.service.MailService;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * GKislin
 */

@RestController
@RequestMapping(value = "/api/mail", produces = MediaType.APPLICATION_JSON_VALUE)
public class MailController {

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/test", method = GET)
    public String test() {
        return "test";
    }

    @RequestMapping(value = "/to-user", method = POST)
    public String sendToUser(@Param("template") String template, @Param("email") String email) {
        return mailService.sendEmail(template, email);
    }

    @RequestMapping(value = "/to-project", method = POST)
    public String sendToGroup(@Param("template") String template, @Param("project") String project, @Param("groupType") String groupType) {
        return mailService.sendGroup(template, project, GroupType.valueOf(groupType.toUpperCase()));
    }
}
