package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.entity.ProjectConsultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectConsultantRepository extends JpaRepository<ProjectConsultant, Integer> {

    @Query("SELECT pc FROM ProjectConsultant pc WHERE pc.project.id = :projectId")
    ProjectConsultant findByProjectId(@Param("projectId") int projectId);
}
