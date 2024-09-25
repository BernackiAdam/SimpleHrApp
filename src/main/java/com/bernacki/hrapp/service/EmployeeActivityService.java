package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.EmployeeActivity;

import java.util.List;

public interface EmployeeActivityService {
    List<EmployeeActivity> findAllCurrentActivities();
    List<EmployeeActivity> findActivitiesByEmployeeId(int employeeId);
}
