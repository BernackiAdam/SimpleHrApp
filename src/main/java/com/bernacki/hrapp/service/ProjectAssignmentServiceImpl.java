package com.bernacki.hrapp.service;

import com.bernacki.hrapp.entity.ProjectAssignment;
import com.bernacki.hrapp.entity.ProjectAssignmentId;
import com.bernacki.hrapp.repository.ProjectAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public void save(ProjectAssignment projectAssignment) {
        projectAssignmentRepository.save(projectAssignment);
    }

    @Override
    public List<ProjectAssignmentId> findProjectAssignmentIdsByEmployeeId(int employeeId) {
        return projectAssignmentRepository.findProjectAssignmentIdsByEmployeeId(employeeId);
    }

    @Override
    public List<ProjectAssignmentId> findProjectAssignmentIdsByProjectId(int projectId) {
        return projectAssignmentRepository.findProjectAssignmentIdsByProjectId(projectId);
    }

    @Transactional
    @Override
    public void deleteAllByProjectId(int projectId) {
        projectAssignmentRepository.deleteProjectAssignmentByProjectId(projectId);
    }

    @Override
    public void deleteAllByIds(List<ProjectAssignmentId> ids) {
        projectAssignmentRepository.deleteAllById(ids);
    }
}
