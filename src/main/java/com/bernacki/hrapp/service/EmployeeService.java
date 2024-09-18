package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    public List<Employee> findAll();
    public Employee findById(int id);
    void save(Employee employee);
    Page<Employee> findAllPaginated(int page, int size);
    Page<Employee> findByFullName(String firstName, String lastName, int page, int size);
    Page<Employee> findByEmail(String email, int page, int size);
    Page<Employee> findByTelephoneNumber(String telephoneNumber, int page, int size);
    Page<Employee> findBySeniority(String seniority, int page, int size);
    Page<Employee> findByPosition(String position, int page, int size);
    Page<Employee> findBySeniorityAndPosition(String seniority, String position, int page, int size);
}
