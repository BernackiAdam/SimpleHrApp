package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectPhase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    public List<Project> findAll();
    public Project findById(int id);
    public Project findByName(String name);
    public List<ProjectPhase> findPhasesAssignedToProject(int id);
    public List<Project> findProjectAssignedToClient(int id);
    public void save(Project project);
    public Page<Project> findAllPaginated(Pageable pageable);
    public Page<Project> findByTitle(String title, Pageable pageable);
    public Page<Project> findByProjectType(String projectType, Pageable pageable);
    public Page<Project> findByCurrentPhase(String phase, Pageable pageable);
    public Page<Project> getProjectPageSearched(String searchBy, Map<String, String> searchParams, Pageable pageable);
}
