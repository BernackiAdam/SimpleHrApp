package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectPhase;
import com.bernacki.hrapp.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Page<Project> findAllPaginated(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }


    @Override
    public Page<Project> findByTitle(String title, Pageable pageable) {
        String searchPattern = "%" + title + "%";
        return projectRepository.findByTitleLikeIgnoreCase(searchPattern, pageable);
    }

    @Override
    public Page<Project> findByProjectType(String projectType, Pageable pageable) {
        return projectRepository.findByProjectType(projectType, pageable);
    }

    @Override
    public Page<Project> findByCurrentPhase(String phase, Pageable pageable) {
        return projectRepository.findByCurrentPhase(phase, pageable);
    }
}
