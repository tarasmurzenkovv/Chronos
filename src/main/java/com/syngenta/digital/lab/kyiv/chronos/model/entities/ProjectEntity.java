package com.syngenta.digital.lab.kyiv.chronos.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "PROJECT")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Column(name = "PROJECT_DESCRIPTION")
    private String projectDescription;
    @ManyToOne
    @JoinColumn(name = "PROJECT_TYPE_ID")
    private ProjectTypeEntity projectTypeEntity;
}
