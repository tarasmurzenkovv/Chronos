package com.syngenta.digital.lab.kyiv.chronos.controllers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.LoginRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<GeneralResponse<UserDto>> registerUser(@Valid @RequestBody UserDto userDto) {
        log.info("About to register the following user '{}'", userDto);
        UserDto savedUserDto = userService.registerUser(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(GeneralResponse.buildResponse(savedUserDto));
    }

    @PostMapping("/user/login")
    public ResponseEntity<GeneralResponse<UserDto>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("About to login the following user '{}'", loginRequest);
        UserDto foundUserInformation = userService.loginRequest(loginRequest);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(GeneralResponse.buildResponse(foundUserInformation));
    }

    @GetMapping("/user/{id}/task")
    public ResponseEntity<GeneralResponse<List<TaskDto>>> findAllTasksForUserId(@PathVariable("id") long id) {
        log.info("About to find task by id '{}'", id);
        List<TaskDto> userTasks = userService.findForUserId(id);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(GeneralResponse.buildResponse(userTasks));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<GeneralResponse<List<TaskDto>>> findAllTasksForUserIdAndTimePeriod(
            @PathVariable("id") long userId,
            @RequestParam("start")
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate start,
            @RequestParam("end")
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate end) {

        log.info("About to find tasks by user id '{}', start '{}', end '{}'", userId, start, end);
        List<TaskDto> userTasks = userService.findForUserIdAndDateRange(userId, start, end);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(GeneralResponse.buildResponse(userTasks));
    }
}
