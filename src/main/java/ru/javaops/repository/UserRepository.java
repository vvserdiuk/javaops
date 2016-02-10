package ru.javaops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.model.GroupType;
import ru.javaops.model.User;

import java.util.List;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u " +
            " LEFT JOIN u.roles WHERE u.email=:email")
    User findByEmail(@Param("email") String email);

    @Query(" SELECT u FROM User u " +
            "  JOIN u.userGroups ug" +
            "  JOIN ug.group g " +
            " WHERE g.type=:groupType AND g.project.name=:projectName AND u.active=TRUE")
    List<User> findByProjectAndGroupType(@Param("projectName") String projectName, @Param("groupType") GroupType GroupType);
}