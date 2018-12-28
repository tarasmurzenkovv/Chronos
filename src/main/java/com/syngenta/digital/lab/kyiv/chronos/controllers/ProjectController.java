package com.syngenta.digital.lab.kyiv.chronos.controllers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/api/v0/project")
    public ResponseEntity<GeneralResponse<ProjectDto>> addNewProject(@RequestBody ProjectDto projectDto) {
        log.info("About to register the following project '{}'", projectDto);
        ProjectDto savedProjectDto = projectService.registerProject(projectDto);
        GeneralResponse<ProjectDto> generalResponse = new GeneralResponse<>(false, savedProjectDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generalResponse);
    }

    @GetMapping("/api/v0/project/{id}")
    public ResponseEntity<GeneralResponse<ProjectDto>> find(@PathVariable("id") long id) {
        log.info("About to find by id '{}'", id);
        ProjectDto savedProjectDto = projectService.find(id);
        GeneralResponse<ProjectDto> generalResponse = new GeneralResponse<>(false, savedProjectDto);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(generalResponse);
    }

    @GetMapping("/api/v0/project")
    public ResponseEntity<GeneralResponse<List<ProjectDto>>> findAll() {
        List<ProjectDto> savedProjectDto = projectService.findAll();
        GeneralResponse<List<ProjectDto>> generalResponse = new GeneralResponse<>(false, savedProjectDto);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(generalResponse);
    }

    @PutMapping("/api/v0/project/")
    public ResponseEntity<GeneralResponse<ProjectDto>> update(@RequestBody ProjectDto projectDto) {
        log.info("About to register the following project '{}'", projectDto);
        ProjectDto savedProjectDto = projectService.update(projectDto);
        GeneralResponse<ProjectDto> generalResponse = new GeneralResponse<>(false, savedProjectDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalResponse);
    }

    @DeleteMapping("/api/v0/project/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        log.info("About to delete project by id '{}'", id);
        projectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
