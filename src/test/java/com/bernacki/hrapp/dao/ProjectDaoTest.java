package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectPhase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectDaoTest extends DaoBaseTestClass{

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private Project project;

    @Autowired
    private Employee employee;

    @Test
    public void checkIfProjectsOfUsersAreLoaded() throws NoSuchFieldException {
        Project project1 = projectDao.findById(1);
        Collection<Employee> employees;
        if(checkIfManyToManyLazyInit(Project.class, "employees")){
            employees = projectDao.findEmployeesAssignedToProject(1);
        }
        else{
            employees = project1.getEmployees();
        }
        assertFalse(employees.isEmpty(), "List should not be empty");
    }

    @Test
    public void checkIfDataIsCorrect() throws NoSuchFieldException {
        Project project1 = projectDao.findById(1);
        Collection<Employee> employees;
        if(checkIfManyToManyLazyInit(Project.class, "employees")){
            employees = projectDao.findEmployeesAssignedToProject(1);
        }
        else{
            employees = project1.getEmployees();
        }
        String employeeEmail = employees.stream().map(Employee::getEmail).findFirst().orElse("none");
        assertEquals("ab@email.com", employeeEmail, "Email should be equal ab@email.com");
    }

    @Test
    public void checkIfProjectPhasesAreLoaded() throws NoSuchFieldException {
        Project project1 = projectDao.findById(1);
        Collection<ProjectPhase> projectPhases;
        if(checkIfOneToManyLazyInit(Project.class, "phases")){
            projectPhases = projectDao.findPhasesAssignedToProject(1);
        }
        else{
            projectPhases = project1.getPhases();
        }
        assertFalse(projectPhases.isEmpty(), "Phases should not be empty");
    }
}
