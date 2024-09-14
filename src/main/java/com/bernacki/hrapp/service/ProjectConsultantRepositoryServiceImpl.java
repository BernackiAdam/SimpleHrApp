package com.bernacki.hrapp.service;

import com.bernacki.hrapp.dao.ProjectConsultantRepository;
import com.bernacki.hrapp.model.ProjectConsultant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectConsultantRepositoryServiceImpl implements ProjectConsultantRepositoryService{

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
}
