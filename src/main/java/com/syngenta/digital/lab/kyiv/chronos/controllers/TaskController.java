package com.syngenta.digital.lab.kyiv.chronos.controllers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/api/v0/task")
    public ResponseEntity<GeneralResponse<TaskDto>> addNewTask(@RequestBody TaskDto taskDto) {
        log.info("About to register the following task '{}'", taskDto);
        TaskDto savedTaskDto = taskService.registerTask(taskDto);
        GeneralResponse<TaskDto> generalResponse = new GeneralResponse<>(false, savedTaskDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generalResponse);
    }

    @GetMapping("/api/v0/task/{id}")
    public ResponseEntity<GeneralResponse<TaskDto>> findTask(@PathVariable("id") long id) {
        log.info("About to find task by id '{}'", id);
        TaskDto savedTaskDto = taskService.find(id);
        GeneralResponse<TaskDto> generalResponse = new GeneralResponse<>(false, savedTaskDto);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(generalResponse);
    }

    @GetMapping("/api/v0/user/{id}/task")
    public ResponseEntity<GeneralResponse<List<TaskDto>>> findAllTasksForUserId(@PathVariable("id") long id) {
        log.info("About to find task by id '{}'", id);
        List<TaskDto> userTasks = taskService.findForUserId(id);
        GeneralResponse<List<TaskDto>> generalResponse = new GeneralResponse<>(false, userTasks);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(generalResponse);
    }

    @GetMapping("/api/v0/user/{id}")
    public ResponseEntity<GeneralResponse<List<TaskDto>>> findAllTasksForUserIdAndTimePeriod(
            @PathVariable("id") long userId,
            @RequestParam("start") LocalDate start,
            @RequestParam("end") LocalDate end) {

        log.info("About to find tasks by user id '{}', start '{}', end '{}'", userId, start, end);
        List<TaskDto> userTasks = taskService.findForUserIdAndDateRange(userId, start, end);
        GeneralResponse<List<TaskDto>> generalResponse = new GeneralResponse<>(false, userTasks);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(generalResponse);
    }

    @PutMapping("/api/v0/task")
    public ResponseEntity<GeneralResponse<TaskDto>> updateTask(@RequestBody TaskDto taskDto) {
        log.info("About to update task for reques '{}'", taskDto);
        TaskDto savedTaskDto = taskService.update(taskDto);
        GeneralResponse<TaskDto> generalResponse = new GeneralResponse<>(false, savedTaskDto);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(generalResponse);
    }

    @DeleteMapping("/api/v0/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") long id) {
        log.info("About to delete task for id '{}'", id);
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
