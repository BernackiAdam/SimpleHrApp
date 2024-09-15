package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectPhase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
//    public List<Project> findAll();
//    public Project findById(int id);
    public Project findByTitle(String title);

    @Query("SELECT pp FROM ProjectPhase pp LEFT JOIN pp.project p WHERE p.id=:projectId")
    public List<ProjectPhase> findPhasesAssignedToProject(@Param("projectId") int id);

    @Query("SELECT p FROM Project p WHERE p.client.id =: clientId")
    public List<Project> findProjectAssignedToClient(@Param("clientId") int id);

//    @Query("SELECT p FROM Project p WHERE p.projectConsultant IS NULL")
//    public List<Project> findProjectsWithoutConsultant();

//    public void save(Project project);
}
