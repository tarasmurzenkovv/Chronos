package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.mappers.ProjectTypeMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectTypeDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectTypeEntity;
import com.syngenta.digital.lab.kyiv.chronos.repositories.ProjectTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectTypeService {
    private final ProjectTypeRepository projectTypeRepository;
    private final ProjectTypeMapper projectTypeMapper;

    public ProjectTypeDto saveNew(ProjectTypeDto projectTypeDto) {
        ProjectTypeEntity projectTypeEntity = projectTypeMapper.mapToEntity(projectTypeDto);
        ProjectTypeEntity savedProjectTypeEntity = projectTypeRepository.save(projectTypeEntity);

        return projectTypeMapper.mapToDto(savedProjectTypeEntity);
    }

    public List<ProjectTypeDto> findAll() {
        return projectTypeRepository.findAll()
                .stream()
                .map(projectTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public ProjectTypeDto findById(long id) {
        return projectTypeRepository.findById(id)
                .map(projectTypeMapper::mapToDto)
                .orElseThrow(() -> new RuntimeException("Cannot find for id " + id));
    }

    @Transactional
    public ProjectTypeDto update(ProjectTypeDto projectTypeDto) {
        ProjectTypeEntity projectTypeEntity = projectTypeMapper.mapToEntity(projectTypeDto);
        ProjectTypeEntity savedProjectTypeEntity = projectTypeRepository.save(projectTypeEntity);

        return projectTypeMapper.mapToDto(savedProjectTypeEntity);
    }

    @Transactional
    public void delete(long id) {
        projectTypeRepository.deleteById(id);
    }
}
