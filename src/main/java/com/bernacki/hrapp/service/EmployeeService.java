package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Employee;

import java.util.List;

public interface EmployeeService {
    public List<Employee> findAll();
    public Employee findById(int id);
}
