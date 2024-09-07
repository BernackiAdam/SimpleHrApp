package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;

import java.util.List;

public interface EmployeeDao {
    public List<Employee> findAll();
    public Employee findById(int id);
    public List<Project> findProjectAssignedToEmployee(int id);
}
