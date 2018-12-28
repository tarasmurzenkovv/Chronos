package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTypeRepository extends JpaRepository<ProjectTypeEntity, Long> {
}
