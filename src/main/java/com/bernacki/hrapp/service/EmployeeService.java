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
    Page<Employee> findAllPaginated(Pageable pageable);
    Page<Employee> findByFullName(String firstName, String lastName, Pageable pageable);
    Page<Employee> findByEmail(String email, Pageable pageable);
    Page<Employee> findByTelephoneNumber(String telephoneNumber, Pageable pageable);
    Page<Employee> findBySeniority(String seniority, Pageable pageable);
    Page<Employee> findByPosition(String position, Pageable pageable);
    Page<Employee> findBySeniorityAndPosition(String seniority, String position, Pageable pageable);
    Page<Employee> getEmployeeListSearched(String searchBy, Map<String, String> searchParams, Pageable pageable, boolean onlyActiveUsers);
    Page<Employee> findAllSearchedAndSortedWithActivities(String searchBy, Map<String, String> searchParams, String sortBy, String sortDirection,Pageable pageable, boolean onlyActiveUsers);
    Employee findEmployeeWithCurrentActivityByEmployeeId(int employeeId);
    void delete(int id);
}
