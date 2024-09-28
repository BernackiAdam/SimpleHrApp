package com.bernacki.hrapp.service;

import com.bernacki.hrapp.entity.Employee;
import com.bernacki.hrapp.entity.EmployeeActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class ScheduleServiceTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeActivityService employeeActivityService;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIfUsersAreReactivated(){
        Employee testEmployee1 = new Employee();
        testEmployee1.setId(1);
        EmployeeActivity testActivity1 = new EmployeeActivity(false);
        testActivity1.setEmployee(testEmployee1);
        testActivity1.setReactivationDate(Date.from(Instant.now().minusSeconds(3600)));
        testEmployee1.setEmployeeActivities(List.of(testActivity1));

        Employee testEmployee2 = new Employee();
        testEmployee2.setId(2);
        EmployeeActivity testActivity2 = new EmployeeActivity(false);
        testActivity2.setEmployee(testEmployee2);
        testActivity2.setReactivationDate(Date.from(Instant.now().plusSeconds(3600)));
        testEmployee2.setEmployeeActivities(List.of(testActivity2));

        List<Employee> employees = List.of(testEmployee1, testEmployee2);

        when(employeeService.findInactiveEmployeesWithCurrentActivity()).thenReturn(employees);

        scheduleService.reactivateUsersSchedule();

        verify(employeeActivityService, times(1)).save(any(EmployeeActivity.class));
        verify(employeeActivityService).save(argThat(activity ->
                activity.getEmployee().getId() == 2 && activity.isActive()));
    }
}
