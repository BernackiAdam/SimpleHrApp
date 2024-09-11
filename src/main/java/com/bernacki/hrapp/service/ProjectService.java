package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectPhase;

import java.util.List;

public interface ProjectService {
    public List<Project> findAll();
    public Project findById(int id);
    public Project findByName(String name);
    public List<ProjectPhase> findPhasesAssignedToProject(int id);
    public List<Project> findProjectAssignedToClient(int id);


}
