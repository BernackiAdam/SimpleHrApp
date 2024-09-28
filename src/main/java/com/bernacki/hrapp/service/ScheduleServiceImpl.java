package com.bernacki.hrapp.service;

import com.bernacki.hrapp.entity.Employee;
import com.bernacki.hrapp.entity.EmployeeActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private EmployeeService employeeService;
    private EmployeeActivityService employeeActivityService;

    @Autowired
    public ScheduleServiceImpl(EmployeeService employeeService, EmployeeActivityService employeeActivityService) {
        this.employeeService = employeeService;
        this.employeeActivityService = employeeActivityService;
    }

    @Override
    @Scheduled(cron = "0 0 2 * * ?")
    public void reactivateUsersSchedule() {
        Date currentDate = Date.from(Instant.now());
        List<Employee> employees = employeeService.findInactiveEmployeesWithCurrentActivity();
        for(Employee emp : employees){
            Optional<EmployeeActivity> result = emp.getEmployeeActivities().stream().findFirst();
            if(result.isPresent() && result.get().getReactivationDate().after(currentDate)){
                EmployeeActivity employeeActivity = new EmployeeActivity();
                employeeActivity.setEmployee(emp);
                employeeActivity.setDate(currentDate);
                employeeActivity.setActive(true);
                employeeActivityService.save(employeeActivity);
            }
        }
    }
}
