package com.syngenta.digital.lab.kyiv.chronos.model.entities.task;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.TagEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "TASK_TAGS")
@Getter
@Setter
@ToString
public class TaskTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID")
    private TaskEntity task;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private TagEntity tag;
}
