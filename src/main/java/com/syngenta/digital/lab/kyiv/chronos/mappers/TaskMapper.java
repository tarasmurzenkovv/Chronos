package com.syngenta.digital.lab.kyiv.chronos.mappers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.TaskEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
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
        taskEntity.setTags(taskDto.getTags());
        taskEntity.setComments(taskDto.getComments());

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
        taskDto.setTags(taskEntity.getTags());

        return taskDto;
    }
}
