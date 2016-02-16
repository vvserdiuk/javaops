package ru.javaops.util;

import ru.javaops.model.User;
import ru.javaops.to.UserTo;

import static ru.javaops.util.Util.assignNotEmpty;

/**
 * GKislin
 * 16.02.2016
 */
public class UserUtil {
    public static User createFromTo(UserTo userTo) {
        return new User(userTo.getEmail(), userTo.getNameSurname(), userTo.getLocation(), userTo.getInfoSource());
    }

    public static void updateFromTo(User user, UserTo userTo) {
        assignNotEmpty(userTo.getNameSurname(), user::setFullName);
        assignNotEmpty(userTo.getLocation(), user::setLocation);
        assignNotEmpty(userTo.getInfoSource(), user::setInfoSource);
        user.setActive(true);
    }
}
