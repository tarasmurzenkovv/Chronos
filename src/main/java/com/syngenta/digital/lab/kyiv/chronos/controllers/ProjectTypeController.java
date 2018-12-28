package com.syngenta.digital.lab.kyiv.chronos.controllers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectTypeDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.service.ProjectTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProjectTypeController {
    private final ProjectTypeService projectTypeService;

    @PostMapping("/api/v0/project-type")
    public ResponseEntity<GeneralResponse<ProjectTypeDto>> addNewProjectType(@RequestBody ProjectTypeDto projectTypeDto) {
        log.info("About to register the following project type '{}'", projectTypeDto);
        ProjectTypeDto savedProjectTypeDto = projectTypeService.saveNew(projectTypeDto);
        GeneralResponse<ProjectTypeDto> generalResponse = new GeneralResponse<>(false, savedProjectTypeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generalResponse);
    }

    @GetMapping("/api/v0/project-type")
    public ResponseEntity<GeneralResponse<List<ProjectTypeDto>>> findAll() {
        log.info("About to find all project types");
        List<ProjectTypeDto> allProjectTypes = projectTypeService.findAll();
        GeneralResponse<List<ProjectTypeDto>> generalResponse = new GeneralResponse<>(false, allProjectTypes);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(generalResponse);
    }

    @GetMapping("/api/v0/project-type/{id}")
    public ResponseEntity<GeneralResponse<ProjectTypeDto>> find(@PathVariable("id") long id) {
        log.info("About to find for project type id " + id);
        ProjectTypeDto projectType = projectTypeService.findById(id);
        GeneralResponse<ProjectTypeDto> generalResponse = new GeneralResponse<>(false, projectType);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(generalResponse);
    }

    @PutMapping("/api/v0/project-type")
    public ResponseEntity<GeneralResponse<ProjectTypeDto>> update(@RequestBody ProjectTypeDto projectTypeDto) {
        log.info("About to register the following project type '{}'", projectTypeDto);
        ProjectTypeDto projectType = projectTypeService.update(projectTypeDto);
        GeneralResponse<ProjectTypeDto> generalResponse = new GeneralResponse<>(false, projectType);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalResponse);
    }

    @DeleteMapping("/api/v0/project-type/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        log.info("About to delete project type id " + id);
        projectTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
