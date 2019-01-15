package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.mappers.TaskMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.TaskEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.TaskException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.ProjectRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TaskRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private static final int ERROR_CODE = 10;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskDto register(TaskDto taskDto) {
        UserEntity userEntity = userRepository.findById(taskDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Cannot find user for id" + taskDto.getUserId()));
        ProjectEntity projectEntity = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Cannot find project for id" + taskDto.getProjectId()));

        TaskEntity taskEntity = taskMapper.mapToEntity(taskDto, userEntity, projectEntity);
        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
        return taskMapper.mapToDto(savedTaskEntity);
    }

    public TaskDto find(long taskId) {
        return taskRepository.findById(taskId)
                .map(taskMapper::mapToDto)
                .orElseThrow(() -> new TaskException(ERROR_CODE, "Cannot find task for id " + taskId));
    }

    @Transactional
    public void delete(long taskId) {
        TaskDto taskDto = this.find(taskId);
        taskRepository.deleteById(taskDto.getTaskId());
    }

}
