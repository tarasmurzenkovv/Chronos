package com.syngenta.digital.lab.kyiv.chronos.model.entities.project;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectTypeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Embedded;
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
@ToString
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Column(name = "PROJECT_DESCRIPTION")
    private String projectDescription;
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "color", column = @Column(name = "COLOR"))
    })
    private ProjectSettings projectSettings = new ProjectSettings();
    @ManyToOne
    @JoinColumn(name = "PROJECT_TYPE_ID")
    private ProjectTypeEntity projectTypeEntity;
    @Column(name = "DELETED", columnDefinition = "bit(1)")
    private boolean deleted;
}
