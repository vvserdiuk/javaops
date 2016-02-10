package ru.javaops.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.model.GroupType;
import ru.javaops.service.MailService;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * GKislin
 */

@RestController
@RequestMapping(value = "/api/mail", produces = MediaType.APPLICATION_JSON_VALUE)
public class MailController {

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/to-user", method = POST)
    public ResponseEntity<String> sendToUser(@Param("template") String template, @Param("email") String email) {
        String result = mailService.sendToUser(template, email);
        return new ResponseEntity<>(result, result.equals(MailService.OK) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/to-project", method = POST)
    public ResponseEntity<MailService.GroupResult> sendToProjectByGroupType(@Param("template") String template, @Param("project") String project, @Param("groupType") String groupType) {
        MailService.GroupResult groupResult = mailService.sendToProjectByGroupType(template, project, GroupType.valueOf(groupType.toUpperCase()));
        return new ResponseEntity<>(groupResult, groupResult.isOk() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/to-group", method = POST)
    public ResponseEntity<MailService.GroupResult> sendToGroup(@Param("template") String template, @Param("group") String group) {
//        MailService.GroupResult groupResult = new MailService.GroupResult(1,2,"123");
        MailService.GroupResult groupResult = mailService.sendToGroup(template, group);
        return new ResponseEntity<>(groupResult, groupResult.isOk() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
