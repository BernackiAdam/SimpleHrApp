package com.bernacki.hrapp.service;

import com.bernacki.hrapp.dao.ProjectAssignmentRepository;
import com.bernacki.hrapp.model.ProjectAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectAssignmentServiceImpl implements ProjectAssignmentService{

    private ProjectAssignmentRepository projectAssignmentRepository;

    @Autowired
    public ProjectAssignmentServiceImpl(ProjectAssignmentRepository projectAssignmentRepository) {
        this.projectAssignmentRepository = projectAssignmentRepository;
    }

    @Override
    public List<ProjectAssignment> findProjectsAssignedToEmployeeWithRolesById(int id) {
        return projectAssignmentRepository.findProjectsAssignedToEmployeeWithRolesById(id);
    }

    @Override
    public List<ProjectAssignment> findEmployeesAssignedToProjectWithRolesById(int id) {
        return projectAssignmentRepository.findEmployeesAssignedToProjectWithRolesById(id);
    }
}
