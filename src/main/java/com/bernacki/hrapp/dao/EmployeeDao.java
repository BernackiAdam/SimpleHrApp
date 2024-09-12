package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Employee;

import java.util.List;

public interface EmployeeDao {
    public List<Employee> findAll();
    public Employee findById(int id);
    public void save(Employee employee);
}
