package ru.javaops.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * A Group.
 */
@Entity
@Table(name = "groups")
public class Group extends NamedEntity {

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "type")
    private GroupType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;


    @OneToMany(mappedBy = "group")
    private Set<UserGroup> groupUsers;

    public Set<UserGroup> getGroupUsers() {
        return groupUsers;
    }

    public Project getProject() {
        return project;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + getId() +
                ", name=" + name +
                '}';
    }
}
