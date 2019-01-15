package com.syngenta.digital.lab.kyiv.chronos.controllers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.ProjectTypeDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.service.ProjectTypeService;
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
public class ProjectTypeController {
    private final ProjectTypeService projectTypeService;

    @PostMapping("/project-type")
    public ResponseEntity<GeneralResponse<ProjectTypeDto>> save(@RequestBody ProjectTypeDto projectTypeDto) {
        log.info("About to register the following project type '{}'", projectTypeDto);
        return GeneralResponse.from(projectTypeService.register(projectTypeDto), HttpStatus.CREATED);
    }

    @GetMapping("/project-type")
    public ResponseEntity<GeneralResponse<List<ProjectTypeDto>>> find() {
        log.info("About to find all project types");
        return GeneralResponse.from(projectTypeService.find(), HttpStatus.FOUND);
    }

    @GetMapping("/project-type/{id}")
    public ResponseEntity<GeneralResponse<ProjectTypeDto>> find(@PathVariable("id") long id) {
        log.info("About to find for project type id " + id);
        return GeneralResponse.from(projectTypeService.find(id), HttpStatus.FOUND);
    }

    @PutMapping("/project-type")
    public ResponseEntity<GeneralResponse<ProjectTypeDto>> update(@RequestBody ProjectTypeDto projectTypeDto) {
        log.info("About to register the following project type '{}'", projectTypeDto);
        return GeneralResponse.from(projectTypeService.register(projectTypeDto), HttpStatus.OK);
    }

    @DeleteMapping("/project-type/{id}")
    public ResponseEntity<GeneralResponse<ProjectTypeDto>> delete(@PathVariable("id") long id) {
        log.info("About to delete project type id " + id);
        projectTypeService.delete(id);
        return GeneralResponse.from(HttpStatus.OK);
    }
}
