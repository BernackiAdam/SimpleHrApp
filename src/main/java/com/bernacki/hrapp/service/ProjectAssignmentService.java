package com.bernacki.hrapp.service;

import com.bernacki.hrapp.entity.ProjectAssignment;
import com.bernacki.hrapp.entity.ProjectAssignmentId;

import java.util.List;

public interface ProjectAssignmentService {
    public List<ProjectAssignment> findProjectsAssignedToEmployeeWithRolesById(int id);
    public List<ProjectAssignment> findEmployeesAssignedToProjectWithRolesById(int id);
    public void save(ProjectAssignment projectAssignment);
    public List<ProjectAssignmentId> findProjectAssignmentIdsByEmployeeId(int employeeId);
    public List<ProjectAssignmentId> findProjectAssignmentIdsByProjectId(int projectId);
    public void deleteAllByIds(List<ProjectAssignmentId> projectAssignmentIdList);
    public void deleteAllByProjectId(int projectId);
}
