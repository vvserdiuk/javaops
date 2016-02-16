package ru.javaops.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.model.*;
import ru.javaops.repository.GroupRepository;
import ru.javaops.repository.UserGroupRepository;
import ru.javaops.repository.UserRepository;
import ru.javaops.to.UserTo;
import ru.javaops.util.UserUtil;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * GKislin
 * 15.02.2016
 */
@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

    public Group findByName(String name) {
        Group group = groupRepository.findByName(name);
        checkNotNull(group, "Не найдена группа '" + name + '\'');
        return group;
    }

    public Set<Group> findByUserId(int userId) {
        return groupRepository.findByUser(userId);
    }

    @Transactional
    public UserGroup addToGroup(UserTo userTo, String groupName, Channel channel) {
        User user = userRepository.findByEmail(userTo.getEmail());
        Group group = findByName(groupName);
        ParticipationType participationType;

        if (user != null) {
            UserUtil.updateFromTo(user, userTo);
            Set<Group> groups = findByUserId(user.getId());
            if (groups.stream().filter(g -> g.equals(group)).findFirst().isPresent()) {
                return new UserGroup(user, group, ParticipationType.DUPLICATED, channel);
            }
            participationType = groups.stream().filter(g -> group.getProject().equals(g.getProject())).findFirst().isPresent() ?
                    ParticipationType.REPEAT : ParticipationType.REGISTERED;
        } else {
            user = UserUtil.createFromTo(userTo);
            participationType = ParticipationType.FIRST_REGISTERED;
        }
        userRepository.save(user);
        UserGroup userGroup = new UserGroup(user, group, participationType, channel);
        userGroupRepository.save(userGroup);
        return userGroup;
    }
}
