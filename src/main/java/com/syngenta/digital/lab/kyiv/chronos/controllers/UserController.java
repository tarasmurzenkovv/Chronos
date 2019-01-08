package com.syngenta.digital.lab.kyiv.chronos.controllers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.LoginRequest;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.UserDto;
import com.syngenta.digital.lab.kyiv.chronos.model.response.GeneralResponse;
import com.syngenta.digital.lab.kyiv.chronos.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/v0/user")
    public ResponseEntity<GeneralResponse<UserDto>> registerUser(@Valid @RequestBody UserDto userDto) {
        log.info("About to register the following user '{}'", userDto);
        UserDto savedUserDto = userService.registerUser(userDto);
        GeneralResponse<UserDto> generalResponse = new GeneralResponse<>(false, savedUserDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generalResponse);
    }

    @PostMapping("/api/v0/user/login")
    public ResponseEntity<GeneralResponse<UserDto>> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("About to login the following user '{}'", loginRequest);
        UserDto savedUserDto = userService.loginRequest(loginRequest);
        GeneralResponse<UserDto> generalResponse = new GeneralResponse<>(false, savedUserDto);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(generalResponse);
    }
}
