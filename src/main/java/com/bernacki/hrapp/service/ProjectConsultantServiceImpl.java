package com.bernacki.hrapp.service;

import com.bernacki.hrapp.entity.ProjectConsultant;
import com.bernacki.hrapp.repository.ProjectConsultantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectConsultantServiceImpl implements ProjectConsultantService {

    @Autowired
    private ProjectConsultantRepository consultantRepository;

    @Override
    public ProjectConsultant findByProjectId(int id) {
        return consultantRepository.findByProjectId(id);
    }

    @Override
    public void save(ProjectConsultant consultant) {
        consultantRepository.save(consultant);
    }

    @Override
    public void deleteByProjectId(int projectId) {
        ProjectConsultant projectConsultant = consultantRepository.findByProjectId(projectId);
        consultantRepository.delete(projectConsultant);
    }
}
