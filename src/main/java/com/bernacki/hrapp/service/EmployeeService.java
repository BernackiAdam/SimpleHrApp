package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    public List<Employee> findAll();
    public Employee findById(int id);
    void save(Employee employee);
    void delete(int id);

    Page<Employee> findAllSearchedAndSortedWithActivities(String searchBy, Map<String, String> searchParams, String sortBy, String sortDirection,Pageable pageable, boolean onlyActiveUsers);
    Employee findEmployeeWithCurrentActivityByEmployeeId(int employeeId);
    List<Employee> findInactiveEmployeesWithCurrentActivity();

}
