package ru.javaops.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.LoggedUser;
import ru.javaops.model.*;
import ru.javaops.repository.GroupRepository;
import ru.javaops.repository.UserGroupRepository;
import ru.javaops.repository.UserRepository;
import ru.javaops.to.UserTo;
import ru.javaops.util.UserUtil;

import java.util.Collection;
import java.util.Set;

/**
 * Authenticate a user from the database.
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService, org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Override
    public LoggedUser loadUserByUsername(final String email) {
        String lowercaseLogin = email.toLowerCase();
        log.debug("Authenticating {}", email);
        User user = userRepository.findByEmail(lowercaseLogin);
        if (user == null) {
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }
        if (!user.isActive()) {
            throw new DisabledException("User " + lowercaseLogin + " was not activated");
        }
        return new LoggedUser(user);
    }


    @Transactional
    public void deleteByEmail(String email) {
        User user = userRepository.findByEmail(email);
        userRepository.delete(user);
    }

    public Collection<User> getGroup(String groupName) {
        return userRepository.findByGroupName(groupName);
    }

    @Override
    @Transactional
    public UserGroup addToGroup(UserTo userTo, String groupName, Channel channel) {
        User user = userRepository.findByEmail(userTo.getEmail());
        Group group = groupRepository.findByName(groupName);
        ParticipationType participationType;

        if (user != null) {
            UserUtil.updateFromTo(user, userTo);
            Set<Group> groups = groupRepository.findByUser(user);
            if (groups.stream().filter(g -> g.equals(group)).findFirst().isPresent()) {
                return new UserGroup(user, group, ParticipationType.DUPLICATED, channel);
            }
            participationType = groups.stream().filter(g -> group.getProject().equals(g.getProject())).findFirst().isPresent() ?
                    ParticipationType.REPEAT : ParticipationType.REGISTERED;
        } else {
            user = UserUtil.createFromTo(userTo);
            participationType = ParticipationType.REGISTERED;
        }
        userRepository.save(user);
        UserGroup userGroup = new UserGroup(user, group, participationType, channel);
        userGroupRepository.save(userGroup);
        return userGroup;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User u) {
        userRepository.save(u);
    }
}
