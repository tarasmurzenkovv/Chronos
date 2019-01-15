package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.mappers.TagMapper;
import com.syngenta.digital.lab.kyiv.chronos.mappers.TaskMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TagDto;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.TagEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.task.TaskEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.task.TaskTagEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.TaskException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.ProjectRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TaskRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private static final int ERROR_CODE = 10;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TagService tagService;
    private final TagMapper tagMapper;

    @Transactional
    public TaskDto register(TaskDto taskDto) {
        UserEntity userEntity = userRepository.findById(taskDto.getUserId())
                .orElseThrow(() -> new TaskException(ERROR_CODE, "Cannot find user for id" + taskDto.getUserId()));
        ProjectEntity projectEntity = projectRepository.findById(taskDto.getProjectId())
                .orElseThrow(() -> new TaskException(ERROR_CODE, "Cannot find project for id" + taskDto.getProjectId()));

        TaskEntity savedTaskEntity = saveTask(taskDto, userEntity, projectEntity);
        List<TagEntity> savedTagEntities = tagService.saveTags(taskDto);
        tagService.saveTaskTags(savedTaskEntity, savedTagEntities);
        TaskDto processedTaskDto = taskMapper.mapToDto(savedTaskEntity);
        processedTaskDto.setTags(savedTagEntities.stream().map(this.tagMapper::mapToDto).collect(Collectors.toList()));
        return processedTaskDto;
    }

    private TaskEntity saveTask(TaskDto taskDto, UserEntity userEntity, ProjectEntity projectEntity) {
        TaskEntity taskEntity = taskMapper.mapToEntity(taskDto, userEntity, projectEntity);
        return taskRepository.save(taskEntity);
    }

    @Transactional(readOnly = true)
    public TaskDto find(long taskId) {
        TaskDto taskDto = taskRepository.findById(taskId)
                .map(taskMapper::mapToDto)
                .orElseThrow(() -> new TaskException(ERROR_CODE, "Cannot find task for id " + taskId));

        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() -> new TaskException(ERROR_CODE, "Cannot find task for id " + taskId));

        List<TagEntity> tagEntities = taskEntity.getTaskTags().stream().map(TaskTagEntity::getTag).collect(Collectors.toList());
        List<TagDto> tagDtos = tagEntities.stream().map(this.tagMapper::mapToDto).collect(Collectors.toList());

        taskDto.setTags(tagDtos);
        return taskDto;
    }

    @Transactional
    public void delete(long taskId) {
        TaskDto taskDto = this.find(taskId);
        taskRepository.deleteById(taskDto.getTaskId());
    }

    @Transactional(readOnly = true)
    public List<TagDto> findTags(long id) {
        return taskRepository.findById(id)
                .map(TaskEntity::getTaskTags)
                .map(taskTags -> taskTags.stream().map(TaskTagEntity::getTag).map(tagMapper::mapToDto).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
