package com.bernacki.hrapp.service;

import com.bernacki.hrapp.entity.EmployeeActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeActivityService {
    Page<EmployeeActivity> findAllCurrentActivities(Pageable pageable);
    Page<EmployeeActivity> findActivitiesByEmployeeId(int employeeId, Pageable pageable);
    Page<EmployeeActivity> findActivitiesByEmployeeIdReversed(int employeeId, Pageable pageable);
    void save(EmployeeActivity employeeActivity);

}
