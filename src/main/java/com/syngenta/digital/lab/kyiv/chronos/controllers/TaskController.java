package com.syngenta.digital.lab.kyiv.chronos.controllers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.service.TaskService;
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

@Slf4j
@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<GeneralResponse<TaskDto>> save(@RequestBody TaskDto taskDto) {
        log.info("About to register the following task '{}'", taskDto);
        return GeneralResponse.from(taskService.register(taskDto), HttpStatus.CREATED);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<GeneralResponse<TaskDto>> find(@PathVariable("id") long id) {
        log.info("About to find task by id '{}'", id);
        return GeneralResponse.from(taskService.find(id), HttpStatus.FOUND);
    }

    @PutMapping("/task")
    public ResponseEntity<GeneralResponse<TaskDto>> update(@RequestBody TaskDto taskDto) {
        log.info("About to update task for request '{}'", taskDto);
        return GeneralResponse.from(taskService.register(taskDto), HttpStatus.FOUND);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<GeneralResponse<TaskDto>> delete(@PathVariable("id") long id) {
        log.info("About to delete task for id '{}'", id);
        taskService.delete(id);
        return GeneralResponse.from(HttpStatus.OK);
    }
}
