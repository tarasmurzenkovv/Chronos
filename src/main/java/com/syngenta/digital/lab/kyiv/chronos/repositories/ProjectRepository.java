package com.syngenta.digital.lab.kyiv.chronos.repositories;

import com.syngenta.digital.lab.kyiv.chronos.model.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
