package ru.javaops.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * GKislin
 * 02.09.2015.
 */
@Entity
@Table(name = "payment")
public class Payment extends BaseEntity {
    @Column(name = "date")
    private Date date;

    @Column(name = "sum")
    private int sum;

    @Column(name = "currency")
    private int currency;

    @Column(name = "comment")
    private int comment;
}
