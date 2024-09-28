package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.entity.ProjectAssignment;
import com.bernacki.hrapp.entity.ProjectAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, ProjectAssignmentId> {

    @Query("SELECT pa FROM ProjectAssignment pa WHERE pa.employee.id=:employeeId")
    public List<ProjectAssignment> findProjectsAssignedToEmployeeWithRolesById(@Param("employeeId") int id);

    @Query("SELECT pa FROM ProjectAssignment pa WHERE pa.project.id=:projectId")
    public List<ProjectAssignment> findEmployeesAssignedToProjectWithRolesById(@Param("projectId") int id);

    @Query("SELECT pa.id FROM ProjectAssignment pa WHERE pa.id.employeeId=:employeeId")
    public List<ProjectAssignmentId> findProjectAssignmentIdsByEmployeeId(@Param("employeeId") int employeeId);

    @Query("SELECT pa.id FROM ProjectAssignment pa WHERE pa.id.projectId=:projectId")
    public List<ProjectAssignmentId> findProjectAssignmentIdsByProjectId(@Param("projectId") int projectId);

    @Modifying
    @Query("DELETE FROM ProjectAssignment pa WHERE pa.id.projectId=:projectId")
    public void deleteProjectAssignmentByProjectId(@Param("projectId") int projectId);

    public ProjectAssignment findByEmployeeIdAndProjectId(int employeeId, int projectId);
}
