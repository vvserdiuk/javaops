package ru.javaops.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.model.User;
import ru.javaops.service.UserService;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * GKislin
 */

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = DELETE)
    public void delete(@Param("email") String email) {
        userService.deleteByEmail(email);
    }

    @RequestMapping(value = "/group", method = POST)
    public Collection<User> sendToGroup(@Param("group") String group) {
//      TODO make json projection
        return userService.getGroup(group);
    }
}
