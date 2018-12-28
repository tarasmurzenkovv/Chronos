package com.syngenta.digital.lab.kyiv.chronos.mappers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectTypeEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectMapper {

    public ProjectEntity maptToEntity(ProjectDto projectDto, ProjectTypeEntity projectTypeEntity) {
        ProjectEntity projectEntity = new ProjectEntity();
        Long id = projectDto.getId();
        if (id != null) {
            projectEntity.setId(id);
        }

        projectEntity.setProjectName(projectDto.getProjectName());
        projectEntity.setProjectDescription(projectDto.getProjectDescription());
        projectEntity.setProjectTypeEntity(projectTypeEntity);

        return projectEntity;
    }

    public ProjectDto mapToDto(ProjectEntity projectEntity) {
        ProjectDto projectDto = new ProjectDto();

        projectDto.setId(projectEntity.getId());
        projectDto.setProjectDescription(projectEntity.getProjectDescription());
        projectDto.setProjectName(projectEntity.getProjectName());
        projectDto.setProjectTypeId(projectEntity.getProjectTypeEntity().getId());

        return projectDto;
    }
}
