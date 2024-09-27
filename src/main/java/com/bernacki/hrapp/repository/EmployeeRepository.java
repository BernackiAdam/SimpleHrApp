package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.employeeActivities ea " +
            "WHERE (:onlyActive = false OR ea.active=true) " +
            "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee = e)" +
            "ORDER BY ea.active DESC, e.id ASC")
    Page<Employee> findAllOrActiveWithCurrentActivity(@Param("onlyActive") boolean onlyActive, Pageable pageable);


    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.employeeActivities ea " +
            "WHERE ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 where ea2.employee = e) " +
            "AND e.id =:employeeId")
    Employee findEmployeeWithCurrentActivityByEmployeeId(@Param("employeeId") int employeeId);

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.employeeActivities ea " +
            "WHERE ea.active=false " +
            "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 where ea2.employee = e)")
    List<Employee> findInactiveEmployeesWithCurrentActivity();
}
