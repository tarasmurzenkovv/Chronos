package com.syngenta.digital.lab.kyiv.chronos.service.validation.task;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.TaskException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskProjectExistenceValidationService implements TaskValidationService {
    private static final int ERROR_CODE = 11;

    @Override
    public void validate(TaskDto taskDto) {

    }
}
