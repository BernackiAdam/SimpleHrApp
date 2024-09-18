package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectAssignment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ProjectAssignmentTest {

    @Autowired
    private Project project;

    @Autowired
    private Employee employee;

    @Autowired
    private ProjectAssignment assignment;

    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepository;

    @Test
    public void checkIfEmployeesAssignedToProjAreLoadedById(){
        List<ProjectAssignment> assignments = projectAssignmentRepository.findEmployeesAssignedToProjectWithRolesById(1);
        for(ProjectAssignment assign : assignments){
            assertFalse(assign.getEmployee().getFirstName().isEmpty(), "Employee name should not be empty");
            assertFalse(assign.getRole().isEmpty(), "Role should not be empty");
        }
    }

    @Test
    public void checkIfProjectsAssignedToEmpAreLoadedById(){
        List<ProjectAssignment> assignments = projectAssignmentRepository.findProjectsAssignedToEmployeeWithRolesById(1);
        for(ProjectAssignment assign : assignments){
            assertFalse(assign.getProject().getTitle().isEmpty(), "Project title should not be empty");
            assertFalse(assign.getRole().isEmpty(), "Role should not be empty");
        }
    }
}
