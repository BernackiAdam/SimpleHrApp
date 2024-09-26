package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.EmployeeActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeActivityRepository extends JpaRepository<EmployeeActivity, Integer> {

    @Query("SELECT ea FROM EmployeeActivity ea " +
            "WHERE ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee.id = ea.employee.id) " +
            "GROUP BY ea.employee.id")
    Page<EmployeeActivity> findAllCurrentActivities(Pageable pageable);

    @Query("SELECT ea FROM EmployeeActivity ea WHERE ea.employee.id=:employeeId")
    Page<EmployeeActivity> findActivitiesByEmployeeId(@Param("employeeId") int employeeId, Pageable pageable);

    @Query("SELECT ea FROM EmployeeActivity ea WHERE ea.employee.id=:employeeId ORDER BY ea.date DESC")
    Page<EmployeeActivity> findActivitiesByEmployeeIdReversed(@Param("employeeId") int employeeId, Pageable pageable);

}
