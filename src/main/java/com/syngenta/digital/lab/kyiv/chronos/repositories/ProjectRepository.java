package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.project.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    @Query("select count(project.id) from ProjectEntity project where project.id=:projectId ")
    Long exists(@Param("projectId") Long projectId);

    @Query("select project.deleted from ProjectEntity project where project.id=:projectId ")
    boolean isDeleted(@Param("projectId") Long projectId);
}
