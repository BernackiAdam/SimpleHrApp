package com.bernacki.hrapp.service;

import com.bernacki.hrapp.entity.ProjectConsultant;

public interface ProjectConsultantService {
    ProjectConsultant findByProjectId(int id);
    void save(ProjectConsultant consultant);
    void deleteByProjectId(int projectId);
}
