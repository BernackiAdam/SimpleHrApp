package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.EmployeeActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeActivityRepository extends JpaRepository<EmployeeActivity, Integer> {

    @Query("SELECT ea FROM EmployeeActivity ea " +
            "WHERE ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee.id = ea.employee.id) " +
            "GROUP BY ea.employee.id")
    List<EmployeeActivity> findAllCurrentActivities();

    @Query("SELECT ea FROM EmployeeActivity ea WHERE ea.employee.id=:employeeId")
    List<EmployeeActivity> findActivitiesByEmployeeId(@Param("employeeId") int employeeId);
}
