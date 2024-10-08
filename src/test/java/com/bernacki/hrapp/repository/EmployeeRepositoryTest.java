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
public class EmployeeRepositoryTest{

    private EmployeeRepository employeeRepository;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository, JdbcTemplate jdbcTemplate) {
        this.employeeRepository = employeeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    public void beforeEach(){
        jdbcTemplate.execute("ALTER TABLE employee ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("INSERT INTO employee (first_name, last_name, email, tel_nr, seniority, position) " +
                "VALUES('TestName1', 'TestSurname1', 'test1@email.com', '123123123', 'Junior', 'Backend Developer')");
        jdbcTemplate.execute("INSERT INTO employee (first_name, last_name, email, tel_nr, seniority, position) " +
                "VALUES('TestName2', 'TestSurname2', 'test2@email.com', '123123123', 'Senior', 'Frontend Developer')");

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
    public void checkIfEmployeesAreLoaded(){
        List<Employee> employees = employeeRepository.findAll();
        assertFalse(employees.isEmpty(), "List should not be empty");
    }

    @Test
    public void checkIfEmployeeByIdIsLoaded(){
        Optional<Employee> result = employeeRepository.findById(1);
        Employee employee = null;
        if(result.isPresent()){
            employee = result.get();
        }
        assertFalse(employee.getFirstName().isEmpty(), "Employee name should not be empty");
    }


    @Test
    public void checkIfAllEmployeesAreLoadedWithActivities(){
        Pageable pageable = PageRequest.of(0, 100);
        Page<Employee> employeePage = employeeRepository.findAllOrActiveWithCurrentActivity(false, pageable);
        assertNotNull(employeePage);
        assertFalse(employeePage.isEmpty());
        assertEquals(2, employeePage.getTotalElements(), "List should contain 2 elements");
        assertThat(employeePage).flatExtracting(Employee::getEmployeeActivities)
                .extracting(EmployeeActivity::isActive).containsExactlyInAnyOrder(false, false);
    }

    @Test
    public void checkIfEmployeeIsSaved(){
        Employee employee = new Employee("TestFirstName", "TestLastName", "test@email.com", "123123123");
        employeeRepository.save(employee);
        Optional<Employee> result = employeeRepository.findById(2);
        Employee employeeAfterSave = result.orElse(null);
        assertNotNull(employeeAfterSave);
    }

    @Test
    public void checkIfEmployeeIsUpdated(){
        Optional<Employee> result = employeeRepository.findById(1);
        Employee employee = result.orElse(null);
        assertNotNull(employee);
        employee.setFirstName("TestFirstName");
        employeeRepository.save(employee);
        Optional<Employee> result2 = employeeRepository.findById(1);
        Employee employeeAfterUpdate = result.orElse(null);
        assertEquals("TestFirstName", employeeAfterUpdate.getFirstName(), "First name should equal TestFirstName");
    }

    @Test
    public void checkIfEmployeeIsLoadedWithActivityById(){
        Employee employee = employeeRepository.findEmployeeWithCurrentActivityByEmployeeId(1);
        assertNotNull(employee);
        assertEquals("TestName1", employee.getFirstName());
        assertEquals(1, employee.getEmployeeActivities().size());
        assertThat(employee.getEmployeeActivities()).flatExtracting(EmployeeActivity::isActive).containsExactlyInAnyOrder(false);
    }

    @Test
    public void checkIfOnlyInactiveEmployeesAreLoadedWithCurrentActivities(){
        jdbcTemplate.execute("INSERT INTO employee_activity (employee_id, active, date) " +
                "VALUES(1, true, '2024-01-03')");
        List<Employee> employees = employeeRepository.findInactiveEmployeesWithCurrentActivity();
        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
        assertThat(employees).flatExtracting(Employee::getEmployeeActivities)
                .extracting(EmployeeActivity::isActive).contains(false);
    }
}
