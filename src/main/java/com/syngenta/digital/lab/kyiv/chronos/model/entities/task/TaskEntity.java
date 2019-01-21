package com.syngenta.digital.lab.kyiv.chronos.model.entities.task;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.project.ProjectEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TASK")
@Getter
@Setter
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

    @OneToMany(mappedBy = "task")
    private List<TaskTagEntity> taskTags = new ArrayList<>();

    @Column(name = "REPORTING_DATE")
    private LocalDate reportingDate;
    @Column(name = "SPENT_TIME")
    private Float spentTime;
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "TAGS")
    private String tags;
    @Column(name = "EDITABLE")
    private boolean editable;
}
