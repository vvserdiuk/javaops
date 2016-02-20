package ru.javaops.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.model.Currency;
import ru.javaops.model.Payment;
import ru.javaops.service.GroupService;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * GKislin
 */

@RestController
@RequestMapping(value = "/api/usergroup", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupController {

    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/pay", method = POST)
    public void pay(@Param("project") String project, @Param("email") String email,
                         @Param("sum") int sum, @Param("currency") Currency currency, @Param("comment") String comment) {
        // TODO add Hibernate and make return
        groupService.pay(email, project, new Payment(sum, currency, comment));
    }
}
