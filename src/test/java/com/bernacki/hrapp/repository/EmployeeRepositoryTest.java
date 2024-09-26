package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.EmployeeActivity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
//    @Disabled
    public void checkIfEmployeesAreLoadedByFullNameLike(){
        String firstNameSearchPattern = "%ame1%";
        String lastNameSearchPattern = "%1%";
        Pageable pageable = PageRequest.of(0, 100);
        Page<Employee> employees = employeeRepository.findByFirstNameLikeAndLastNameLikeIgnoreCase(firstNameSearchPattern, lastNameSearchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "TestName1";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
    }

    @Test
    public void checkIfEmployeesAreLoadedByEmailLike(){
        String searchPattern = "%" + "1" + "%";
        Pageable pageable = PageRequest.of(0, 100);

        Page<Employee> employees = employeeRepository.findByEmailLike(searchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "test1@email.com";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
    }

    @Test
    public void checkIfEmployeesAreLoadedByTelephoneNumberLike(){
        String searchPattern = "%" + "123" + "%";
        Pageable pageable = PageRequest.of(0, 100);

        Page<Employee> employees = employeeRepository.findByTelephoneNumberLike(searchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "TestName1";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
    }

    @Test
    public void checkIfEmployeesAreLoadedBySeniority(){
        String searchPattern = "Junior";
        Pageable pageable = PageRequest.of(0, 100);

        Page<Employee> employees = employeeRepository.findBySeniority(searchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "TestName1";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
    }

    @Test
    public void checkIfEmployeesAreLoadedByPosition(){
        String searchPattern = "Backend Developer";
        Pageable pageable = PageRequest.of(0, 100);

        Page<Employee> employees = employeeRepository.findByPosition(searchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "TestName1";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
    }

    @Test
    public void checkIfEmployeesAreLoadedBySeniorityAndPosition(){
        String senioritySearchPattern = "Junior";
        String positionSearchPattern = "Backend Developer";
        Pageable pageable = PageRequest.of(0, 100);

        Page<Employee> employees = employeeRepository.findBySeniorityAndPosition(senioritySearchPattern, positionSearchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "TestName1";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
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
}
