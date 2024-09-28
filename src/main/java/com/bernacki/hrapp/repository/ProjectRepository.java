package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.entity.Project;
import com.bernacki.hrapp.entity.ProjectPhase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    public Project findByTitle(String title);
    Page<Project> findByTitleLikeIgnoreCase(String title, Pageable pageable);
    Page<Project> findByProjectType(String projectType, Pageable pageable);

    @Query("SELECT pp FROM ProjectPhase pp LEFT JOIN pp.project p WHERE p.id=:projectId")
    public List<ProjectPhase> findPhasesAssignedToProject(@Param("projectId") int id);

    @Query("SELECT p FROM Project p WHERE p.client.id=:clientId")
    public List<Project> findProjectAssignedToClient(@Param("clientId") int id);

    @Query("SELECT p FROM Project p " +
            "JOIN p.phases ph " +
            "WHERE ph.phase=:phaseName " +
            "AND ph.date=(SELECT MAX(ph2.date) FROM ProjectPhase ph2 WHERE ph2.project = p)")
    public Page<Project> findByCurrentPhase(@Param("phaseName") String phaseName, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Project p WHERE p.id=:projectId")
    public void deleteProjectByProjectId(@Param("projectId") int projectId);

}
