package ru.javaops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.model.Group;
import ru.javaops.model.User;

import java.util.Set;

@Transactional(readOnly = true)
public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Query("SELECT DISTINCT(ug.group) FROM UserGroup ug " +
            " WHERE ug.user = :user")
    Set<Group> findByUser(@Param("user") User user);


    @Query("SELECT g FROM Group g WHERE g.name = :name")
    Group findByName(@Param("name") String name);
}