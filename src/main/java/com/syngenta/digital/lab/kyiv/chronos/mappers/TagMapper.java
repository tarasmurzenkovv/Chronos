package com.syngenta.digital.lab.kyiv.chronos.mappers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.TagDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.TagEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagMapper {

    public List<TagEntity> mapToEntity(List<TagDto> tagDtos) {
        return tagDtos.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

    public List<TagDto> mapToDto(List<TagEntity> entities) {
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TagEntity mapToEntity(TagDto tagDto) {
        TagEntity tagEntity = new TagEntity();

        if (tagDto.getTagId() != null) {
            tagEntity.setId(tagDto.getTagId());
        }
        tagEntity.setTag(tagDto.getTag());

        return tagEntity;
    }

    public TagDto mapToDto(TagEntity tagEntity) {
        TagDto tagDto = new TagDto();

        tagDto.setTagId(tagEntity.getId());
        tagDto.setTag(tagEntity.getTag());

        return tagDto;
    }
}
