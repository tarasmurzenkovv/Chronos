package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    @Query( "select tag  from TagEntity tag where tag.tag like :fuzziedTagName " )
    Stream<TagEntity> findAllMatchingRegex(@Param("fuzziedTagName") String tagName);
}
