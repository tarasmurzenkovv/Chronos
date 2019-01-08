package com.syngenta.digital.lab.kyiv.chronos.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TASK")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"projectEntity", "userEntity"})
@ToString
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private ProjectEntity projectEntity;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity userEntity;
    @Column(name = "REPORTING_DATE")
    private LocalDate reportingDate;
    @Column(name = "SPENT_TIME")
    private Float spentTime;
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "TAGS")
    private String tags;
}
