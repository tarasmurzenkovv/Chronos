package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.mappers.TagMapper;
import com.syngenta.digital.lab.kyiv.chronos.mappers.TaskMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TagDto;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.project.ProjectEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.TagEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.task.TaskEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.TaskException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.ProjectRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TagRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TaskRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final TagRepository tagRepository;

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
        processedTaskDto.setTags(this.tagMapper.mapToDto(savedTagEntities));
        return processedTaskDto;
    }

    private TaskEntity saveTask(TaskDto taskDto, UserEntity userEntity, ProjectEntity projectEntity) {
        TaskEntity taskEntity = taskMapper.mapToEntity(taskDto, userEntity, projectEntity);
        Long taskId = taskEntity.getId();
        if (taskId != null) {
            if (!taskRepository.isEditable(taskId)) {
                throw new TaskException(ERROR_CODE, String.format("The task with id '%s' is already frozen. Cannot modify it.", taskId));
            }
        }
        return taskRepository.save(taskEntity);
    }

    @Transactional(readOnly = true)
    public TaskDto find(long taskId) {
        return taskRepository.findById(taskId)
                .map(taskMapper::mapToDto)
                .map(tagDto -> {
                    tagDto.setTags(findTags(taskId));
                    return tagDto;
                })
                .orElseThrow(() -> new TaskException(ERROR_CODE, "Cannot find task for id " + taskId));
    }

    @Transactional
    public void delete(long taskId) {
        TaskDto taskDto = this.find(taskId);
        taskRepository.deleteById(taskDto.getTaskId());
    }

    @Transactional(readOnly = true)
    public List<TagDto> findTags(long taskId) {
        return tagRepository.findAllTagsForTaskId(taskId).collect(Collectors.toList());
    }
}
