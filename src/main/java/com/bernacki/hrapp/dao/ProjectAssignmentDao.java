package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.ProjectAssignment;

import java.util.List;

public interface ProjectAssignmentDao {
    public List<ProjectAssignment> findProjectsAssignedToEmployeeWithRolesById(int id);
    public List<ProjectAssignment> findEmployeesAssignedToProjectWithRolesById(int id);
}
