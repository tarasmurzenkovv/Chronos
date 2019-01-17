package com.syngenta.digital.lab.kyiv.chronos.mappers;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.TagEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.task.TaskEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.task.TaskTagEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskTagMapper {

    public TaskTagEntity mapToEntity(TaskEntity taskEntity, TagEntity tagEntity) {
        TaskTagEntity taskTagEntity = new TaskTagEntity();

        taskTagEntity.setTag(tagEntity);
        taskTagEntity.setTask(taskEntity);

        return taskTagEntity;
    }
}
