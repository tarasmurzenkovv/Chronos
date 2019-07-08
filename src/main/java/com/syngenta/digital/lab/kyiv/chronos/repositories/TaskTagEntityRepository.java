package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.TagEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.task.TaskEntity;
import com.syngenta.digital.lab.kyiv.chronos.model.entities.task.TaskTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskTagEntityRepository extends JpaRepository<TaskTagEntity, Long> {

    @Query("select taskTagEntity from TaskTagEntity taskTagEntity where taskTagEntity.task=:task and taskTagEntity.tag=:tag ")
    Optional<TaskTagEntity> findByTaskAndTag(@Param("task") TaskEntity taskEntity, @Param("tag") TagEntity tagEntity);

    @Modifying
    @Query(" delete from TaskTagEntity taskTag where taskTag.tag.id=:id  ")
    void deleteTaskTags(@Param("id") long tagId);
}
