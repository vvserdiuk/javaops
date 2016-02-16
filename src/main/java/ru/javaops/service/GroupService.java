package ru.javaops.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.javaops.model.Group;
import ru.javaops.repository.GroupRepository;

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

    @Cacheable("group")
    public Group findByName(String name) {
        Group group = groupRepository.findByName(name);
        checkNotNull(group, "Не найдена группа '" + name + '\'');
        return group;
    }

    public Set<Group> findByUserId(int userId) {
        return groupRepository.findByUser(userId);
    }
}
