package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    public List<Employee> findAll();
    public Employee findById(int id);
    void save(Employee employee);
    Page<Employee> findAllPaginated(int page, int size);
}
