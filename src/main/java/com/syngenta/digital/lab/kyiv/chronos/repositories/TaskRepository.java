package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("select task from TaskEntity task " +
            "where task.userEntity.id=:id ")
    Stream<TaskEntity> findAllTasksForUserId(@Param("id") Long id);

    @Query("select task from TaskEntity task " +
            "where task.userEntity.id=:id and task.reportingDate>=:startDate and task.reportingDate<=:endDate ")
    Stream<TaskEntity> findAllTasksForUserId(@Param("id") Long id,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);
}
