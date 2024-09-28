package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.entity.Employee;
import com.bernacki.hrapp.entity.EmployeeActivity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "com.bernacki.hrapp.dao")
@TestPropertySource(locations = "/test.properties")
public class EmployeeDaoTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEach(){
        jdbcTemplate.execute("ALTER TABLE employee ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("INSERT INTO employee (first_name, last_name, email, tel_nr, seniority, position) " +
                "VALUES('TestName1', 'TestSurname1', 'test1@email.com', '000000001', 'Junior', 'Backend Developer')");
        jdbcTemplate.execute("INSERT INTO employee (first_name, last_name, email, tel_nr, seniority, position) " +
                "VALUES('TestName2', 'TestSurname2', 'test2@email.com', '000000002', 'Senior', 'Frontend Developer')");
        jdbcTemplate.execute("INSERT INTO employee (first_name, last_name, email, tel_nr, seniority, position) " +
                "VALUES('TestName3', 'TestSurname3', 'test3@email.com', '000000003', 'Senior', 'Backend Developer')");

        jdbcTemplate.execute("INSERT INTO employee_activity (employee_id, active, date) " +
                "VALUES(1, true, '2024-01-01')");
        jdbcTemplate.execute("INSERT INTO employee_activity (employee_id, active, date) " +
                "VALUES(2, true, '2024-01-01')");
        jdbcTemplate.execute("INSERT INTO employee_activity (employee_id, active, date) " +
                "VALUES(3, true, '2024-01-01')");
        jdbcTemplate.execute("INSERT INTO employee_activity (employee_id, active, date, reactivation_date, deactivation_reason) " +
                "VALUES(1, false, '2024-01-02', '2024-01-01', 'Test Reason 1')");
        jdbcTemplate.execute("INSERT INTO employee_activity (employee_id, active, date, reactivation_date, deactivation_reason) " +
                "VALUES(2, false, '2024-01-02', '2024-01-01', 'Test Reason 2')");
//        jdbcTemplate.execute("INSERT INTO employee_activity (employee_id, active, date, reactivation_date, deactivation_reason) " +
//                "VALUES(3, false, '2024-01-02', '2024-01-01', 'Test Reason 3')");

    }

    @AfterEach
    public void afterEach(){
        jdbcTemplate.execute("DELETE FROM employee_activity");
        jdbcTemplate.execute("DELETE FROM employee");
    }

    @Test
    public void checkIfQueryIsExecutingProperly_All(){
        String query = "SELECT e FROM Employee e " +
                "LEFT JOIN FETCH e.employeeActivities ea " +
                "WHERE (:onlyActive=false OR ea.active=true) " +
                "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee=e) " +
                "ORDER BY ea.active DESC, e.id ASC";
        int paramCount=0;
        String firstSearchParam = "";
        String secondSearchParam = "";
        boolean onlyActive = false;

        List<Employee> employees = employeeDao.executeQueryWithSearchingAndSorting(query, paramCount
                , firstSearchParam, secondSearchParam, onlyActive);

        assertFalse(employees.isEmpty());
        assertEquals(3, employees.size());
        assertThat(employees).flatExtracting(Employee::getEmployeeActivities)
                .flatExtracting(EmployeeActivity::isActive).containsExactly(true, false, false);
        assertThat(employees).flatExtracting(Employee::getId).containsExactly(3,1,2);
    }

    @Test
    public void checkIfQueryIsExecutingProperly_fullNameAll(){
        String query = "SELECT e FROM Employee e " +
                "LEFT JOIN FETCH e.employeeActivities ea " +
                "WHERE (:onlyActive=false OR ea.active=true) " +
                "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee=e) " +
                "AND LOWER(e.firstName) LIKE LOWER(:firstSearchParam) " +
                "AND LOWER(e.lastName) LIKE LOWER(:secondSearchParam) " +
                "ORDER BY ea.active DESC, e.id ASC";
        int paramCount=2;
        String firstSearchParam = "%test%";
        String secondSearchParam = "%test%";
        boolean onlyActive = false;

        List<Employee> employees = employeeDao.executeQueryWithSearchingAndSorting(query, paramCount
                , firstSearchParam, secondSearchParam, onlyActive);

        assertFalse(employees.isEmpty());
        assertEquals(3, employees.size());
        assertThat(employees).flatExtracting(Employee::getEmployeeActivities)
                .flatExtracting(EmployeeActivity::isActive).containsExactly(true, false, false);
        assertThat(employees).flatExtracting(Employee::getId).containsExactly(3,1,2);
    }

    @Test
    public void checkIfQueryIsExecutingProperly_fullNameOnlyActive(){
        String query = "SELECT e FROM Employee e " +
                "LEFT JOIN FETCH e.employeeActivities ea " +
                "WHERE (:onlyActive=false OR ea.active=true) " +
                "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee=e) " +
                "AND LOWER(e.firstName) LIKE LOWER(:firstSearchParam) " +
                "AND LOWER(e.lastName) LIKE LOWER(:secondSearchParam) " +
                "ORDER BY ea.active DESC, e.id ASC";
        int paramCount=2;
        String firstSearchParam = "%test%";
        String secondSearchParam = "%test%";
        boolean onlyActive = true;

        List<Employee> employees = employeeDao.executeQueryWithSearchingAndSorting(query, paramCount
                , firstSearchParam, secondSearchParam, onlyActive);

        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
        assertThat(employees).flatExtracting(Employee::getEmployeeActivities)
                .flatExtracting(EmployeeActivity::isActive).containsExactly(true);
        assertThat(employees).flatExtracting(Employee::getId).containsExactly(3);
    }

    @Test
    public void checkIfQueryIsExecutingProperly_fullNameEmptyInputOnlyActive(){
        String query = "SELECT e FROM Employee e " +
                "LEFT JOIN FETCH e.employeeActivities ea " +
                "WHERE (:onlyActive=false OR ea.active=true) " +
                "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee=e) " +
                "AND LOWER(e.firstName) LIKE LOWER(:firstSearchParam) " +
                "AND LOWER(e.lastName) LIKE LOWER(:secondSearchParam) " +
                "ORDER BY ea.active DESC, e.id ASC";
        int paramCount=2;
        String firstSearchParam = "";
        String secondSearchParam = "";
        boolean onlyActive = true;

        List<Employee> employees = employeeDao.executeQueryWithSearchingAndSorting(query, paramCount
                , firstSearchParam, secondSearchParam, onlyActive);

        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
        assertThat(employees).flatExtracting(Employee::getEmployeeActivities)
                .flatExtracting(EmployeeActivity::isActive).containsExactly(true);
        assertThat(employees).flatExtracting(Employee::getId).containsExactly(3);
    }

    @Test
    public void checkIfQueryIsExecutingProperly_emailAll(){
        String query = "SELECT e FROM Employee e " +
                "LEFT JOIN FETCH e.employeeActivities ea " +
                "WHERE (:onlyActive=false OR ea.active=true) " +
                "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee=e) " +
                "AND LOWER(e.email) LIKE LOWER(:firstSearchParam) " +
                "ORDER BY ea.active DESC, e.id ASC";
        int paramCount=1;
        String firstSearchParam = "%test1%";
        String secondSearchParam = "";
        boolean onlyActive = false;

        List<Employee> employees = employeeDao.executeQueryWithSearchingAndSorting(query, paramCount
                , firstSearchParam, secondSearchParam, onlyActive);

        assertFalse(employees.isEmpty());
        assertEquals(1, employees.size());
        assertThat(employees).flatExtracting(Employee::getEmployeeActivities)
                .flatExtracting(EmployeeActivity::isActive).containsExactly(false);
        assertThat(employees).flatExtracting(Employee::getId).containsExactly(1);
    }

    @Test
    public void checkIfQueryIsExecutingProperly_emptyList(){
        String query = "SELECT e FROM Employee e " +
                "LEFT JOIN FETCH e.employeeActivities ea " +
                "WHERE (:onlyActive=false OR ea.active=true) " +
                "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee=e) " +
                "AND LOWER(e.email) LIKE LOWER(:firstSearchParam) " +
                "ORDER BY ea.active DESC, e.id ASC";
        int paramCount=1;
        String firstSearchParam = "%test2%";
        String secondSearchParam = "";
        boolean onlyActive = true;

        List<Employee> employees = employeeDao.executeQueryWithSearchingAndSorting(query, paramCount
                , firstSearchParam, secondSearchParam, onlyActive);

        assertTrue(employees.isEmpty());

    }
}
