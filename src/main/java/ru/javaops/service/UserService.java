package ru.javaops.service;

import ru.javaops.model.User;

import java.util.Collection;

/**
 * GKislin
 * 13.02.2016
 */
public interface UserService {

    void deleteByEmail(String email);

    Collection<User> getGroup(String groupName);
}
