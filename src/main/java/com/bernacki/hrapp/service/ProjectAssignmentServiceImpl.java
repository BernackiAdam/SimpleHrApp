package com.bernacki.hrapp.service;

import com.bernacki.hrapp.dao.ProjectAssignmentDao;
import com.bernacki.hrapp.model.ProjectAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectAssignmentServiceImpl implements ProjectAssignmentService{

    private ProjectAssignmentDao projectAssignmentDao;

    @Autowired
    public ProjectAssignmentServiceImpl(ProjectAssignmentDao projectAssignmentDao) {
        this.projectAssignmentDao = projectAssignmentDao;
    }

    @Override
    public List<ProjectAssignment> findProjectsAssignedToEmployeeWithRolesById(int id) {
        return projectAssignmentDao.findProjectsAssignedToEmployeeWithRolesById(id);
    }

    @Override
    public List<ProjectAssignment> findEmployeesAssignedToProjectWithRolesById(int id) {
        return projectAssignmentDao.findEmployeesAssignedToProjectWithRolesById(id);
    }
}
