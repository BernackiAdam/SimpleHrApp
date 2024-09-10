package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.ProjectAssignment;

import java.util.List;

public interface ProjectAssignmentService {
    public List<ProjectAssignment> findProjectsAssignedToEmployeeWithRolesById(int id);
    public List<ProjectAssignment> findEmployeesAssignedToProjectWithRolesById(int id);
}
