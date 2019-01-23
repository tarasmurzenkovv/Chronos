package com.syngenta.digital.lab.kyiv.chronos.controllers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/project")
    public ResponseEntity<GeneralResponse<ProjectDto>> save(@RequestBody ProjectDto projectDto) {
        log.info("About to register the following project '{}'", projectDto);
        return GeneralResponse.from(projectService.create(projectDto), HttpStatus.CREATED);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<GeneralResponse<ProjectDto>> find(@PathVariable("id") long projectId) {
        log.info("About to find by id '{}'", projectId);
        return GeneralResponse.from(projectService.find(projectId), HttpStatus.FOUND);
    }

    @GetMapping("/project")
    public ResponseEntity<GeneralResponse<List<ProjectDto>>> find() {
        return GeneralResponse.from(projectService.find(), HttpStatus.FOUND);
    }

    @PutMapping("/project")
    public ResponseEntity<GeneralResponse<ProjectDto>> update(@RequestBody ProjectDto projectDto) {
        log.info("About to register the following project '{}'", projectDto);
        ProjectDto savedProjectDto = projectService.update(projectDto);
        return GeneralResponse.from(savedProjectDto, HttpStatus.OK);
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<GeneralResponse<ProjectDto>> delete(@PathVariable("id") long projectId) {
        log.info("About to delete project by id '{}'", projectId);
        projectService.delete(projectId);
        return GeneralResponse.from(HttpStatus.OK);
    }
}
