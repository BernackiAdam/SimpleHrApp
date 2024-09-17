package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
