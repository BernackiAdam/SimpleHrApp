package com.bernacki.hrapp.service;

import com.bernacki.hrapp.dao.ProjectRepository;
import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectPhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(int id) {
        Optional<Project> result = projectRepository.findById(id);
        Project project = null;
        if(result.isPresent()){
            project= result.get();
        }
        return project;
    }

    @Override
    public Project findByName(String name) {
        return projectRepository.findByTitle(name);
    }


    @Override
    public List<ProjectPhase> findPhasesAssignedToProject(int id) {
        return projectRepository.findPhasesAssignedToProject(id);
    }

    @Override
    public List<Project> findProjectAssignedToClient(int id) {
        return projectRepository.findProjectAssignedToClient(id);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public Page<Project> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return projectRepository.findAll(pageable);
    }
}
