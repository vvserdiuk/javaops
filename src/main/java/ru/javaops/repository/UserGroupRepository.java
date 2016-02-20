package ru.javaops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.model.UserGroup;

@Transactional(readOnly = true)
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {
    @Query("SELECT ug FROM UserGroup ug WHERE ug.user.id=:userId AND ug.group.id=:groupId")
    UserGroup findByUserIdAndGroupId(@Param("userId") int userId, @Param("groupId") int groupId);
}