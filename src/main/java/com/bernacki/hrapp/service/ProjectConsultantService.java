package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.ProjectConsultant;

public interface ProjectConsultantService {
    ProjectConsultant findByProjectId(int id);
    void save(ProjectConsultant consultant);
}
