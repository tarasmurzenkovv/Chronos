package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.dto.TagDto;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    @Query( "select new com.syngenta.digital.lab.kyiv.chronos.model.dto.TagDto(tag.id, tag.tag) from TagEntity tag " +
            "where substring(tag.tag, 1, length(:tagPrefix) )=:tagPrefix" )
    Stream<TagDto> findAllMatchingRegex(@Param("tagPrefix") String tagPrefix);

    @Query (nativeQuery = true, name = "findAllTagsForTaskId")
    Stream<TagDto> findAllTagsForTaskId(@Param("taskId") Long taskId);
}
