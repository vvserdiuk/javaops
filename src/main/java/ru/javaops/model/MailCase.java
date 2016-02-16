package ru.javaops.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * GKislin
 * 10.01.2016
 */
@Entity
@Table(name = "mail_case")
public class MailCase extends BaseEntity {
    protected MailCase() {
    }

    public MailCase(User user, String template, String result) {
        this.user = user;
        this.template = template;
        this.result = result;
        this.datetime = new Date();
    }

    @Column(name = "datetime", columnDefinition = "TIMESTAMP DEFAULT NOW()", nullable = false)
    @NotNull
    private Date datetime;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @Column(name = "template", nullable = false)
    @NotNull
    private String template;

    @Column(name = "result", nullable = false)
    @NotNull
    private String result;
}
