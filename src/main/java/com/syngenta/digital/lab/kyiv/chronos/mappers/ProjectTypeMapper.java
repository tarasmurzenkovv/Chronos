package com.syngenta.digital.lab.kyiv.chronos.mappers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectTypeDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectTypeEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectTypeMapper {
    public ProjectTypeEntity mapToEntity(ProjectTypeDto projectTypeDto) {
        ProjectTypeEntity projectTypeEntity = new ProjectTypeEntity();
        Long id = projectTypeDto.getId();
        if (id != null) {
            projectTypeEntity.setId(id);
        }
        projectTypeEntity.setProjectTypeName(projectTypeDto.getProjectType());

        return projectTypeEntity;
    }

    public ProjectTypeDto mapToDto(ProjectTypeEntity projectTypeEntity) {
        ProjectTypeDto projectTypeDto = new ProjectTypeDto();

        projectTypeDto.setId(projectTypeEntity.getId());
        projectTypeDto.setProjectType(projectTypeEntity.getProjectTypeName());

        return projectTypeDto;
    }
}
