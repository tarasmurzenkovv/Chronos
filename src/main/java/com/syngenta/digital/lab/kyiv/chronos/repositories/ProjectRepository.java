package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.project.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    @Query("select count(project.id) from ProjectEntity project where project.id=:projectId ")
    Long exists(@Param("projectId") Long projectId);

    @Query("select new com.syngenta.digital.lab.kyiv.chronos.model.dto" +
            ".ProjectDto(project.id, project.projectName, project.projectDescription, project.projectTypeEntity.id, project.projectSettings.color, project.deleted) " +
            "from ProjectEntity project ")
    Stream<ProjectDto> findAllWithJoinFetch();
}
