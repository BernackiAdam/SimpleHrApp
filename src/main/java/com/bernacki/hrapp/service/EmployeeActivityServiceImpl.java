package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.EmployeeActivity;
import com.bernacki.hrapp.repository.EmployeeActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeActivityServiceImpl implements EmployeeActivityService{

    private EmployeeActivityRepository employeeActivityRepository;

    @Autowired
    public EmployeeActivityServiceImpl(EmployeeActivityRepository employeeActivityRepository) {
        this.employeeActivityRepository = employeeActivityRepository;
    }

    @Override
    public Page<EmployeeActivity> findAllCurrentActivities(Pageable pageable) {
        return employeeActivityRepository.findAllCurrentActivities(pageable);
    }

    @Override
    public Page<EmployeeActivity> findActivitiesByEmployeeId(int employeeId, Pageable pageable) {
        return employeeActivityRepository.findActivitiesByEmployeeId(employeeId, pageable);
    }

    @Override
    public Page<EmployeeActivity> findActivitiesByEmployeeIdReversed(int employeeId, Pageable pageable) {
        return employeeActivityRepository.findActivitiesByEmployeeIdReversed(employeeId, pageable);
    }
}
