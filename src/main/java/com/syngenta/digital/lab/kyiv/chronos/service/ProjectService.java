package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.mappers.ProjectMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectTypeEntity;
import com.syngenta.digital.lab.kyiv.chronos.repositories.ProjectRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.ProjectTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectTypeRepository projectTypeRepository;
    private final ProjectMapper projectMapper;

    public ProjectDto registerProject(ProjectDto projectDto) {
        ProjectTypeEntity projectTypeEntity = projectTypeRepository.findById(projectDto.getProjectTypeId())
                .orElseThrow(() -> new RuntimeException("Cannot find project type for id" + projectDto.getProjectTypeId()));
        ProjectEntity projectEntity = projectMapper.maptToEntity(projectDto, projectTypeEntity);
        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
        return projectMapper.mapToDto(savedProjectEntity);
    }

    public ProjectDto find(long id) {
        return projectRepository.findById(id)
                .map(projectMapper::mapToDto)
                .orElseThrow(() -> new RuntimeException("Cannot find project for id " + id));
    }

    @Transactional
    public ProjectDto update(ProjectDto projectDto) {
        ProjectTypeEntity projectTypeEntity = projectTypeRepository.findById(projectDto.getProjectTypeId())
                .orElseThrow(() -> new RuntimeException("Cannot find project type for id" + projectDto.getProjectTypeId()));
        ProjectEntity projectEntity = projectMapper.maptToEntity(projectDto, projectTypeEntity);
        ProjectEntity savedProjectEntity = projectRepository.save(projectEntity);
        return projectMapper.mapToDto(savedProjectEntity);
    }

    public List<ProjectDto> findAll() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        projectRepository.deleteById(id);
    }
}
