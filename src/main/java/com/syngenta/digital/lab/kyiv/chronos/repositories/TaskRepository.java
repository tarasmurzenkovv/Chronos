package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("select task from TaskEntity task " +
            "where task.userEntity.id=:userId ")
    Stream<TaskEntity> find(@Param("userId") Long userId);

    @Query("select task from TaskEntity task " +
            "where task.userEntity.id=:userId and task.reportingDate>=:startDate and task.reportingDate<=:endDate ")
    Stream<TaskEntity> find(@Param("userId") Long userId,
                            @Param("startDate") LocalDate startDate,
                            @Param("endDate") LocalDate endDate);
}
