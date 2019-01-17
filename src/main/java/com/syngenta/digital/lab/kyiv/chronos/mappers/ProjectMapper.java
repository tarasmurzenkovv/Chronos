package com.syngenta.digital.lab.kyiv.chronos.mappers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.project.ProjectEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectTypeEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.project.ProjectSettings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectMapper {
    private static final String DEFAULT_COLOR = "#ffffff";

    public ProjectEntity mapToEntity(ProjectDto projectDto, ProjectTypeEntity projectTypeEntity) {
        ProjectEntity projectEntity = new ProjectEntity();
        Long id = projectDto.getId();
        if (id != null) {
            projectEntity.setId(id);
        }

        projectEntity.setProjectName(projectDto.getProjectName());
        projectEntity.setProjectDescription(projectDto.getProjectDescription());
        projectEntity.setProjectTypeEntity(projectTypeEntity);
        projectEntity.getProjectSettings().setColor(ProjectMapper.getColor(projectDto));

        return projectEntity;
    }

    public ProjectDto mapToDto(ProjectEntity projectEntity) {
        ProjectDto projectDto = new ProjectDto();

        projectDto.setId(projectEntity.getId());
        projectDto.setProjectDescription(projectEntity.getProjectDescription());
        projectDto.setProjectName(projectEntity.getProjectName());
        projectDto.setColor(ProjectMapper.getColor(projectEntity));
        projectDto.setProjectTypeId(projectEntity.getProjectTypeEntity().getId());

        return projectDto;
    }

    private static String getColor(ProjectDto projectDto) {
        return StringUtils.isEmpty(projectDto.getColor()) ? DEFAULT_COLOR : projectDto.getColor();
    }

    private static String getColor(ProjectEntity projectEntity) {
        return Optional.ofNullable(projectEntity.getProjectSettings())
                .map(ProjectSettings::getColor)
                .orElse(DEFAULT_COLOR);
    }
}
