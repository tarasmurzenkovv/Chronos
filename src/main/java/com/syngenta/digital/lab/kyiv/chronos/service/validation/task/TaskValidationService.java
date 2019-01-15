package com.syngenta.digital.lab.kyiv.chronos.service.validation.task;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;

public interface TaskValidationService {
    void validate(TaskDto taskDto);
}
