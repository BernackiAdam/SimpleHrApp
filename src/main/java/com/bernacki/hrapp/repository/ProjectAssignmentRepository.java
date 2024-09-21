package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.ProjectAssignment;
import com.bernacki.hrapp.model.ProjectAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, ProjectAssignmentId> {

    @Query("SELECT pa FROM ProjectAssignment pa WHERE pa.employee.id=:employeeId")
    public List<ProjectAssignment> findProjectsAssignedToEmployeeWithRolesById(@Param("employeeId") int id);

    @Query("SELECT pa FROM ProjectAssignment pa WHERE pa.project.id=:projectId")
    public List<ProjectAssignment> findEmployeesAssignedToProjectWithRolesById(@Param("projectId") int id);

    public ProjectAssignment findDistinctByEmployeeIdAndProjectId(int employeeId, int projectId);
}
