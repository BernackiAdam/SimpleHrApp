package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.ProjectPhase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectPhaseRepository extends JpaRepository<ProjectPhase, Integer> {

    @Modifying
    @Query("DELETE FROM ProjectPhase pp WHERE pp.project.id=:projectId")
    void deleteAllByProjectId(@Param("projectId") int projectId);
}
