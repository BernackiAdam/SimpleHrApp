package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.ProjectConsultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectConsultantRepository extends JpaRepository<ProjectConsultant, Integer> {

    @Query("SELECT pc FROM ProjectConsultant pc WHERE pc.project.id = :projectId")
    ProjectConsultant findByProjectId(@Param("projectId") int projectId);
}
