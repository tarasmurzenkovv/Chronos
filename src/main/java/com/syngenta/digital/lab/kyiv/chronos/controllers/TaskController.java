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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<GeneralResponse<TaskDto>> addNewTask(@RequestBody TaskDto taskDto) {
        log.info("About to register the following task '{}'", taskDto);
        TaskDto savedTaskDto = taskService.registerTask(taskDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GeneralResponse.buildResponse(savedTaskDto));
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<GeneralResponse<TaskDto>> findTask(@PathVariable("id") long id) {
        log.info("About to find task by id '{}'", id);
        TaskDto foundTask = taskService.find(id);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(GeneralResponse.buildResponse(foundTask));
    }

    @GetMapping("/user/{id}/task")
    public ResponseEntity<GeneralResponse<List<TaskDto>>> findAllTasksForUserId(@PathVariable("id") long id) {
        log.info("About to find task by id '{}'", id);
        List<TaskDto> userTasks = taskService.findForUserId(id);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(GeneralResponse.buildResponse(userTasks));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<GeneralResponse<List<TaskDto>>> findAllTasksForUserIdAndTimePeriod(
            @PathVariable("id") long userId,
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end) {

        log.info("About to find tasks by user id '{}', start '{}', end '{}'", userId, start, end);
        List<TaskDto> userTasks = taskService.findForUserIdAndDateRange(userId, start, end);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(GeneralResponse.buildResponse(userTasks));
    }

    @PutMapping("/task")
    public ResponseEntity<GeneralResponse<TaskDto>> updateTask(@RequestBody TaskDto taskDto) {
        log.info("About to update task for request '{}'", taskDto);
        TaskDto updatedTask = taskService.update(taskDto);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(GeneralResponse.buildResponse(updatedTask));
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") long id) {
        log.info("About to delete task for id '{}'", id);
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
