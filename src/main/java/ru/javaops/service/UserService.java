package ru.javaops.service;

import ru.javaops.model.Channel;
import ru.javaops.model.User;
import ru.javaops.model.UserGroup;
import ru.javaops.to.UserTo;

import java.util.Collection;

/**
 * GKislin
 * 13.02.2016
 */
public interface UserService {

    void deleteByEmail(String email);

    Collection<User> getGroup(String groupName);

    UserGroup addToGroup(UserTo userTo, String groupName, Channel channel);

    User findByEmail(String email);

    void save(User u);
}
