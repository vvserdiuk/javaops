package ru.javaops.util;

import ru.javaops.model.User;
import ru.javaops.to.UserTo;

import static ru.javaops.util.Util.acceptNotNull;

/**
 * GKislin
 * 16.02.2016
 */
public class UserUtil {
    public static User createFromTo(UserTo userTo) {
        return new User(userTo.getEmail(), userTo.getNameSurname(), userTo.getLocation(), userTo.getInfoSource());
    }

    public static void updateFromTo(User user, UserTo userTo) {
        acceptNotNull(userTo.getNameSurname(), user::setFullName);
        acceptNotNull(userTo.getLocation(), user::setLocation);
        acceptNotNull(userTo.getInfoSource(), user::setInfoSource);
        user.setActive(true);
    }
}
