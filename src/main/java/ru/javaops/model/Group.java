package ru.javaops.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * A Group.
 */
@Entity
@Table(name = "group")
public class Group extends NamedEntity {

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "type")
    private GroupType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @JoinColumn(name = "project_id", nullable = false)
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
