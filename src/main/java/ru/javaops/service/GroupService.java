package ru.javaops.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.model.*;
import ru.javaops.repository.GroupRepository;
import ru.javaops.repository.PaymentRepository;
import ru.javaops.repository.UserGroupRepository;
import ru.javaops.to.UserTo;
import ru.javaops.util.UserUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

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
    private UserService userService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CacheManager cacheManager;

    public Group findByName(String name) {
        Group group = groupRepository.findByName(name);
        checkNotNull(group, "Не найдена группа '" + name + '\'');
        return group;
    }

    public List<Group> getAll() {
        List<Group> groups = groupRepository.findAll(new Sort("name"));
        Cache cache = cacheManager.getCache("group");
        groups.forEach(g -> cache.put(g.getName(), g));
        return groups;
    }

    public Set<Group> findByUserId(int userId) {
        return groupRepository.findByUser(userId);
    }

    @Transactional
    public UserGroup addToGroup(UserTo userTo, ProjectGroups projectGroups, String channel) {
        User user = userService.findByEmail(userTo.getEmail());
        ParticipationType participationType;

        if (user != null) {
            UserUtil.updateFromTo(user, userTo);
            Set<Group> groups = findByUserId(user.getId());
            participationType = groups.stream()
                    .filter(g -> projectGroups.project.equals(g.getProject()) && g.getType() == GroupType.FINISHED)
                    .findFirst().isPresent() ? ParticipationType.REPEAT : ParticipationType.REGISTERED;

            if (groups.stream().filter(g -> g.equals(projectGroups.registeredGroup) || g.equals(projectGroups.currentGroup)).findFirst().isPresent()) {
                // Already registered
                return new UserGroup(user, projectGroups.registeredGroup, participationType, channel);
            }
        } else {
            user = UserUtil.createFromTo(userTo);
            participationType = ParticipationType.FIRST_REGISTERED;
        }
        userService.save(user);
        Group group = (participationType == ParticipationType.REPEAT) ? projectGroups.currentGroup : projectGroups.registeredGroup;
        UserGroup userGroup = new UserGroup(user, group, participationType, channel);
        return userGroupRepository.save(userGroup);
    }

    public UserGroup move(User u, Group sourceGroup, Group targetGroup) {
        UserGroup ug = userGroupRepository.findByUserIdAndGroupId(u.getId(), sourceGroup.getId());
        checkNotNull(ug, "User %s missed in group %s", u.getEmail(), sourceGroup.getName());
        ug.setGroup(targetGroup);
        return ug;
    }

    @Transactional
    public UserGroup pay(String email, String projectName, Payment payment) {
        User u = userService.findExistedByEmail(email);
        ProjectGroups projectGroups = getProjectGroups(projectName);
        UserGroup ug = move(u, projectGroups.registeredGroup, projectGroups.currentGroup);
        paymentRepository.save(payment);
        ug.setPayment(payment);
        userGroupRepository.save(ug);
        return ug;
    }

    public ProjectGroups getProjectGroups(String projectName) {
        List<Group> groups = getAll();
        return new ProjectGroups(
                getGroupByProjectAndType(groups, projectName, GroupType.REGISTERED),
                getGroupByProjectAndType(groups, projectName, GroupType.CURRENT));
    }

    private static Group getGroupByProjectAndType(List<Group> groups, String projectName, GroupType type) {
        Optional<Group> group = groups.stream()
                .filter(g -> g.getProject() != null && g.getProject().getName().equals(projectName) && (g.getType() == type))
                .findFirst();
        checkState(group.isPresent(), "В проекте %s отсутствуют группы c типом %s", projectName, type);
        return group.get();
    }

    public static class ProjectGroups {
        public final Group registeredGroup;
        public final Group currentGroup;
        public final Project project;

        public ProjectGroups(Group registeredGroup, Group currentGroup) {
            this.registeredGroup = registeredGroup;
            this.currentGroup = currentGroup;
            this.project = registeredGroup.getProject();
        }
    }
}
