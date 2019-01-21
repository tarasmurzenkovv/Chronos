package com.syngenta.digital.lab.kyiv.chronos.mappers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.project.ProjectEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.task.TaskEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskMapper {

    public TaskEntity mapToEntity(TaskDto taskDto, UserEntity userEntity, ProjectEntity projectEntity) {
        TaskEntity taskEntity = new TaskEntity();

        if (taskDto.getTaskId() != null) {
            taskEntity.setId(taskDto.getTaskId());
        }

        taskEntity.setUserEntity(userEntity);
        taskEntity.setProjectEntity(projectEntity);
        taskEntity.setReportingDate(taskDto.getReportingDate());
        taskEntity.setSpentTime(taskDto.getSpentTime());
        taskEntity.setComments(taskDto.getComments());
        taskEntity.setEditable(true);

        return taskEntity;
    }

    public TaskDto mapToDto(TaskEntity taskEntity) {
        TaskDto taskDto = new TaskDto();

        taskDto.setTaskId(taskEntity.getId());
        taskDto.setProjectId(taskEntity.getProjectEntity().getId());
        taskDto.setUserId(taskEntity.getUserEntity().getId());
        taskDto.setComments(taskEntity.getComments());
        taskDto.setReportingDate(taskEntity.getReportingDate());
        taskDto.setSpentTime(taskEntity.getSpentTime());

        return taskDto;
    }
}
