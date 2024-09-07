package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectDaoTest {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private Project project;

    @Autowired
    private Employee employee;

    @Test
    public void checkIfProjectsOfUsersAreLoaded(){
        Project project1 = projectDao.findById(1);
        Collection<Employee> employees = project1.getEmployees();
        assertFalse(employees.isEmpty(), "List should not be empty");
    }

    @Test
    public void checkIfDataIsCorrect(){
        Project project1 = projectDao.findById(1);
        Collection<Employee> projects= project1.getEmployees();
        String employeeEmail = projects.stream().map(Employee::getEmail).findFirst().orElse("none");
        assertEquals("ab@email.com", employeeEmail, "Email should be equal ab@email.com");
    }
}
