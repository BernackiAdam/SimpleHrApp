package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.ProjectConsultant;

public interface ProjectConsultantRepositoryService {
    ProjectConsultant findByProjectId(int id);
}
