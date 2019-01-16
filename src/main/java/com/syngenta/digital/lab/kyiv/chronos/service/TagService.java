package com.syngenta.digital.lab.kyiv.chronos.service;

import com.syngenta.digital.lab.kyiv.chronos.mappers.TagMapper;
import com.syngenta.digital.lab.kyiv.chronos.mappers.TaskTagMapper;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TagDto;
import com.syngenta.digital.lab.kyiv.chronos.model.dto.TaskDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.TagEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.task.TaskEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.task.TaskTagEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.exceptions.TagException;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TagRepository;
import com.syngenta.digital.lab.kyiv.chronos.repositories.TaskTagEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private static final int ERROR_CODE = 13;
    private final TagMapper tagMapper;
    private final TaskTagMapper taskTagMapper;
    private final TagRepository tagRepository;
    private final TaskTagEntityRepository taskTagsRepository;

    @Transactional
    public TagDto register(TagDto tagDto) {
        TagEntity tagEntity = tagMapper.mapToEntity(tagDto);
        return tagMapper.mapToDto(tagRepository.save(tagEntity));
    }

    @Transactional(readOnly = true)
    public List<TagDto> findAllTagsMatching(String tagToMatch) {
        return tagRepository.findAllMatchingRegex(tagToMatch)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TagEntity> saveTags(TaskDto taskDto) {
        if (CollectionUtils.isEmpty(taskDto.getTags())) {
            return Collections.emptyList();
        }
        List<TagEntity> tagEntities = taskDto.getTags()
                .stream()
                .map(tagMapper::mapToEntity)
                .collect(Collectors.toList());
        return tagRepository.saveAll(tagEntities);
    }

    @Transactional
    public void saveTaskTags(TaskEntity savedTaskEntity, List<TagEntity> savedTagEntities) {
        List<TaskTagEntity> taskTags = savedTagEntities
                .stream()
                .map(savedTag -> taskTagsRepository.findByTaskAndTag(savedTaskEntity, savedTag)
                        .orElseGet(() -> taskTagMapper.mapToEntity(savedTaskEntity, savedTag)))
                .collect(Collectors.toList());
        taskTagsRepository.saveAll(taskTags);
    }

    @Transactional
    public void delete(long id) {
        taskTagsRepository.deleteTaskTags(id);
        tagRepository.deleteById(id);
    }

    public TagDto find(long id) {
        return tagRepository.findById(id)
                .map(tagMapper::mapToDto)
                .orElseThrow(() -> new TagException(ERROR_CODE, String.format("Cannot find tag for id '%s'", id)));
    }

    public List<TagDto> find() {
        return tagRepository
                .findAll()
                .stream()
                .map(tagMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
