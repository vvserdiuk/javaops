package ru.javaops.model;

import javax.persistence.*;

/**
 * GKislin
 * 02.09.2015.
 */
@Entity
@Table(name = "user_group")
public class UserGroup extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "type")
    private ParticipationType type;

    @Column(name="channel")
    private Channel channel;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Payment payment;
}
