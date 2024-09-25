package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.EmployeeActivity;
import com.bernacki.hrapp.repository.EmployeeActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeActivityServiceImpl implements EmployeeActivityService{

    private EmployeeActivityRepository employeeActivityRepository;

    @Autowired
    public EmployeeActivityServiceImpl(EmployeeActivityRepository employeeActivityRepository) {
        this.employeeActivityRepository = employeeActivityRepository;
    }

    @Override
    public List<EmployeeActivity> findAllCurrentActivities() {
        return employeeActivityRepository.findAllCurrentActivities();
    }

    @Override
    public List<EmployeeActivity> findActivitiesByEmployeeId(int employeeId) {
        return employeeActivityRepository.findActivitiesByEmployeeId(employeeId);
    }
}
