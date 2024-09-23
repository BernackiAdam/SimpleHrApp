package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Page<Employee> findByFirstNameLikeAndLastNameLikeIgnoreCase(String firstName, String lastName, Pageable pageable);
    Page<Employee> findByEmailLike(String email, Pageable pageable);
    Page<Employee> findByTelephoneNumberLike(String telephoneNumber, Pageable pageable);
    Page<Employee> findBySeniority(String seniority, Pageable pageable);
    Page<Employee> findByPosition(String position, Pageable pageable);
    Page<Employee> findDistinctBySeniorityAndPosition(String seniority, String position, Pageable pageable);
}
