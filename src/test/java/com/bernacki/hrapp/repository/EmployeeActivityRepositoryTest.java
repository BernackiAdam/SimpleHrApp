package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.entity.Employee;
import com.bernacki.hrapp.entity.EmployeeActivity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "/test.properties")
public class EmployeeActivityRepositoryTest {

    @Autowired
    private EmployeeActivityRepository employeeActivityRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEach(){
        jdbcTemplate.execute("ALTER TABLE employee ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("INSERT INTO employee (first_name, last_name, email, tel_nr, seniority, position) " +
                "VALUES('TestName1', 'TestSurname1', 'test1@email.com', '123123123', 'Junior', 'Backend Developer')");
        jdbcTemplate.execute("INSERT INTO employee (first_name, last_name, email, tel_nr, seniority, position) " +
                "VALUES('TestName2', 'TestSurname2', 'test2@email.com', '123123123', 'Junior', 'Backend Developer')");

        jdbcTemplate.execute("INSERT INTO employee_activity (employee_id, active, date) " +
                "VALUES(1, true, '2024-01-01')");
        jdbcTemplate.execute("INSERT INTO employee_activity (employee_id, active, date) " +
                "VALUES(2, true, '2024-01-01')");
        jdbcTemplate.execute("INSERT INTO employee_activity (employee_id, active, date, reactivation_date, deactivation_reason) " +
                "VALUES(1, false, '2024-01-02', '2024-01-01', 'On Leave')");
        jdbcTemplate.execute("INSERT INTO employee_activity (employee_id, active, date, reactivation_date, deactivation_reason) " +
                "VALUES(2, false, '2024-01-02', '2024-01-01', 'Company policy')");
    }

    @AfterEach
    public void afterEach(){
        jdbcTemplate.execute("DELETE FROM employee_activity");
        jdbcTemplate.execute("DELETE FROM employee");
    }

    @Test
    public void checkIfCurrentActivitiesAreLoaded(){
        Pageable pageable = PageRequest.of(0, 100);
        Page<EmployeeActivity> employeeActivities= employeeActivityRepository.findAllCurrentActivities(pageable);
        assertNotNull(employeeActivities);
        assertFalse(employeeActivities.isEmpty());
        assertEquals(2, employeeActivities.getTotalElements(), "List should contain 2 elements");

        assertThat(employeeActivities).extracting(EmployeeActivity::getEmployee).extracting(Employee::getId)
                .containsExactlyInAnyOrder(1,2);
    }

    @Test
    public void checkIfActivitiesAreLoaded(){
        List<EmployeeActivity> employeeActivities = employeeActivityRepository.findAll();
        assertFalse(employeeActivities.isEmpty());
        assertEquals(4, employeeActivities.size(), "List should contain 4 elements");
    }

    @Test
    public void checkIfEmployeesActivitiesAreLoaded(){
        Pageable pageable = PageRequest.of(0, 100);
        Page<EmployeeActivity> employeeActivities = employeeActivityRepository.findActivitiesByEmployeeId(1, pageable);
        assertFalse(employeeActivities.isEmpty());
        assertEquals(2, employeeActivities.getTotalElements(), "List should contain 2 elements");
        assertThat(employeeActivities).extracting(EmployeeActivity::getEmployee).extracting(Employee::getId)
                .containsExactlyInAnyOrder(1,1);

    }

    @Test
    public void checkIfEmployeeActivityIsSaved(){
        Optional<Employee> result = employeeRepository.findById(1);
        Employee employee = result.orElse(null);
        assertNotNull(employee);
        assertEquals(2, employee.getEmployeeActivities().size());
        EmployeeActivity employeeActivity = new EmployeeActivity(true);
        employeeActivity.setEmployee(employee);
        employeeActivityRepository.save(employeeActivity);
        Optional<Employee> resultAfterSave = employeeRepository.findById(1);
        Employee employeeAfterSave = result.orElse(null);
        assertNotNull(employeeAfterSave);
        assertEquals(2, employeeAfterSave.getEmployeeActivities().size());
    }
}
