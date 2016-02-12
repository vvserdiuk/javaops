package ru.javaops.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * GKislin
 * 02.09.2015.
 */
@Entity
@Table(name = "payment")
public class Payment extends BaseEntity {
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "sum")
    private int sum;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "comment")
    private String comment;
}
