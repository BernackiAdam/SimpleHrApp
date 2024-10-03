package com.bernacki.hrapp.service;

import com.bernacki.hrapp.dto.ProjectConsultantDto;
import com.bernacki.hrapp.entity.Project;
import com.bernacki.hrapp.entity.ProjectConsultant;

public interface ProjectConsultantService {
    ProjectConsultant findByProjectId(int id);
    void save(ProjectConsultant consultant);
    void deleteByProjectId(int projectId);
    void saveUsingDto(ProjectConsultantDto projectConsultantDto, Project project);
}
