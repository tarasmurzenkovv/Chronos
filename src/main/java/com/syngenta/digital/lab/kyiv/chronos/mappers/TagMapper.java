package com.syngenta.digital.lab.kyiv.chronos.mappers;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.TagDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.TagEntity;
import org.springframework.stereotype.Service;

@Service
public class TagMapper {

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
