package com.bernacki.hrapp.service;

import com.bernacki.hrapp.entity.ProjectPhase;
import com.bernacki.hrapp.repository.ProjectPhaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectPhaseServiceImpl implements ProjectPhaseService{

    private ProjectPhaseRepository projectPhaseRepository;

    @Autowired
    public ProjectPhaseServiceImpl(ProjectPhaseRepository projectPhaseRepository) {
        this.projectPhaseRepository = projectPhaseRepository;
    }

    @Transactional
    @Override
    public void deleteAllByProjectId(int projectId) {
        projectPhaseRepository.deleteAllByProjectId(projectId);
    }

    @Override
    public void save(ProjectPhase projectPhase) {
        projectPhaseRepository.save(projectPhase);
    }
}
