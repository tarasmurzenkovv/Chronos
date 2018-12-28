package com.syngenta.digital.lab.kyiv.chronos.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "PROJECT_TYPE")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProjectTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TASK_TYPE_NAME")
    private String projectTypeName;
}
