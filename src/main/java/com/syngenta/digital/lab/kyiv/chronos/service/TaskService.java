package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.mappers.TaskMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.TaskEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import com.syngenta.digital.lab.kyiv.chronos.repositories.ProjectRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TaskRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskDto registerTask(TaskDto taskDto) {
        UserEntity userEntity = userRepository.findById(taskDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Cannot find user for id" + taskDto.getUserId()));
        ProjectEntity projectEntity = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Cannot find project for id" + taskDto.getProjectId()));

        TaskEntity taskEntity = taskMapper.mapToEntity(taskDto, userEntity, projectEntity);
        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
        return taskMapper.mapToDto(savedTaskEntity);
    }

    public TaskDto find(long id) {
        return taskRepository.findById(id)
                .map(taskMapper::mapToDto)
                .orElseThrow(() -> new RuntimeException("Cannot find task for id " + id));
    }

    @Transactional(readOnly = true)
    public List<TaskDto> findForUserId(long id) {
        return taskRepository.findAllTasksForUserId(id)
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskDto update(TaskDto taskDto) {
        return registerTask(taskDto);
    }

    @Transactional
    public void delete(long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> findForUserIdAndDateRange(long userId, LocalDate start, LocalDate end) {
        return taskRepository
                .findAllTasksForUserId(userId, start, end)
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
