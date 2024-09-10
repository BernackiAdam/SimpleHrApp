package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeDaoTest extends DaoBaseTestClass{
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private Project project;

    @Autowired
    private Employee employee;


    @Test
    public void checkIfEmployeesAreLoaded(){
        List<Employee> employees = employeeDao.findAll();
        assertFalse(employees.isEmpty(), "List should not be empty");
    }

    @Test
    public void checkIfEmployeeByIdIsLoaded(){
        Employee employee1 = employeeDao.findById(1);
        assertFalse(employee1.getFirstName().isEmpty(), "Employee name should not be empty");
    }
}
