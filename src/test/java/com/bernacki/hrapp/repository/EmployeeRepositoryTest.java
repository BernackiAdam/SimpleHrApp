package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EmployeeRepositoryTest extends DaoBaseTestClass{
    @Autowired
    private EmployeeRepository employeeRepository;

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
    public void checkIfEmployeesAreLoadedByFullNameLike(){
        String firstNameSearchPattern = "%"+"ada" + "%";
        String lastNameSearchPattern = "%"+"ber" + "%";
        Pageable pageable = PageRequest.of(0, 100);
        Page<Employee> employees = employeeRepository.findDistinctByFirstNameLikeAndLastNameLikeIgnoreCase(firstNameSearchPattern, lastNameSearchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "Adam";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
    }

    @Test
    public void checkIfEmployeesAreLoadedByEmailLike(){
        String searchPattern = "%" + "ab" + "%";
        Pageable pageable = PageRequest.of(0, 100);

        Page<Employee> employees = employeeRepository.findByEmailLike(searchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "Adam";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
    }

    @Test
    public void checkIfEmployeesAreLoadedByTelephoneNumberLike(){
        String searchPattern = "%" + "123" + "%";
        Pageable pageable = PageRequest.of(0, 100);

        Page<Employee> employees = employeeRepository.findByTelephoneNumberLike(searchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "Adam";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
    }

    @Test
    public void checkIfEmployeesAreLoadedBySeniority(){
        String searchPattern = "Junior";
        Pageable pageable = PageRequest.of(0, 100);

        Page<Employee> employees = employeeRepository.findBySeniority(searchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "Hubert";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
    }

    @Test
    public void checkIfEmployeesAreLoadedByPosition(){
        String searchPattern = "Backend Developer";
        Pageable pageable = PageRequest.of(0, 100);

        Page<Employee> employees = employeeRepository.findByPosition(searchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "Hubert";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
    }

    @Test
    public void checkIfEmployeesAreLoadedBySeniorityAndPosition(){
        String senioritySearchPattern = "Junior";
        String positionSearchPattern = "Backend Developer";
        Pageable pageable = PageRequest.of(0, 100);

        Page<Employee> employees = employeeRepository.findDistinctBySeniorityAndPosition(senioritySearchPattern, positionSearchPattern, pageable);
        assertFalse(employees.isEmpty());
        String expectedFirstName = "Hubert";
        assertTrue(employees.stream().anyMatch(e -> e.getFirstName().equals(expectedFirstName)));
    }
}
