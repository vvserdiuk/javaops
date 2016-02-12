package ru.javaops.model;

import javax.persistence.*;

/**
 * GKislin
 * 02.09.2015.
 */
@Entity
@Table(name = "user_group", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "group_id"}, name = "user_group_unique_idx")})
public class UserGroup extends BaseEntity {

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ParticipationType type;

    @Enumerated(EnumType.STRING)
    @Column(name="channel")
    private Channel channel;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Payment payment;
}
