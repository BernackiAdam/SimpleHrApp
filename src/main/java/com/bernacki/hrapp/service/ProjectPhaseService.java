package com.bernacki.hrapp.service;

import com.bernacki.hrapp.entity.ProjectPhase;

public interface ProjectPhaseService {
    public void deleteAllByProjectId(int projectId);
    public void save(ProjectPhase projectPhase);
}
