package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectPhase;

import java.util.List;

public interface ProjectDao {
    public List<Project> findAll();
    public Project findById(int id);
    public Project findByName(String name);
    public List<ProjectPhase> findPhasesAssignedToProject(int id);
    public List<Project> findProjectAssignedToClient(int id);
    public void save(Project project);
}
