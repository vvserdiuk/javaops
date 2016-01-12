package ru.javaops.model;

import javax.persistence.*;
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

    public MailCase(User user, String subject, String result) {
        this.user = user;
        this.subject = subject;
        this.result = result;
    }

    @Column(name = "date", columnDefinition = "TIMESTAMP DEFAULT NOW()", nullable = false)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "result", nullable = false)
    private String result;
}
