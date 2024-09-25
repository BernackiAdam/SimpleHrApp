package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.EmployeeActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeActivityService {
    Page<EmployeeActivity> findAllCurrentActivities(Pageable pageable);
    Page<EmployeeActivity> findActivitiesByEmployeeId(int employeeId, Pageable pageable);
}
