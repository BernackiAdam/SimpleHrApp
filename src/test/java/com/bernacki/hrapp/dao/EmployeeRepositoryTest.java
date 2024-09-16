package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class EmployeeRepositoryTest extends DaoBaseTestClass{
    @Autowired
    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private Project project;
//
//    @Autowired
//    private Employee employee;


    @Test
    public void checkIfEmployeesAreLoaded(){
        List<Employee> employees = employeeRepository.findAll();
        assertFalse(employees.isEmpty(), "List should not be empty");
    }

    @Test
    public void checkIfEmployeeByIdIsLoaded(){
        Optional<Employee> result = employeeRepository.findById(1);
        Employee employee = null;
        if(result.isPresent()){
            employee = result.get();
        }
        assertFalse(employee.getFirstName().isEmpty(), "Employee name should not be empty");
    }
}
