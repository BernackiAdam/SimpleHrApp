package com.bernacki.hrapp.service;


import com.bernacki.hrapp.dao.EmployeeDao;
import com.bernacki.hrapp.entity.Employee;
import com.bernacki.hrapp.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EmployeeServiceTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void checkIfEmployeesAreReturnedSearchedAndSorted_All() {

        Employee testEmployee1 = new Employee("TestEmp1", "TestEmp1", "TestEmp1", "TestEmp1");
        Employee testEmployee2 = new Employee("TestEmp2", "TestEmp2", "TestEmp2", "TestEmp2");

        List<Employee> mockEmployeeList = List.of(testEmployee1, testEmployee2);

        Map<String, String> searchParams = new TreeMap<>();

        Pageable pageable = PageRequest.of(0, 100);

        String searchBy = "";
        String sortBy = "id";
        String sortDirection = "asc";

        String expectedQuery = "SELECT e FROM Employee e " +
                "LEFT JOIN FETCH e.employeeActivities ea " +
                "WHERE (:onlyActive=false OR ea.active=true) " +
                "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee = e) " +
                "ORDER BY ea.active DESC, e.id ASC";

        when(employeeDao.executeQueryWithSearchingAndSorting(anyString(), anyInt(), anyString(), anyString(), anyBoolean())).thenReturn(mockEmployeeList);

        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> paramCountCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> firstParamCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> secondParamCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> onlyActiveCaptor = ArgumentCaptor.forClass(Boolean.class);

        employeeService.findAllSearchedAndSortedWithActivities(searchBy, searchParams, sortBy, sortDirection, pageable, false);

        verify(employeeDao).executeQueryWithSearchingAndSorting(
                queryCaptor.capture(),
                paramCountCaptor.capture(),
                firstParamCaptor.capture(),
                secondParamCaptor.capture(),
                onlyActiveCaptor.capture()
        );

        assertEquals(expectedQuery, queryCaptor.getValue());
        assertEquals(0, paramCountCaptor.getValue());
        assertFalse(onlyActiveCaptor.getValue());
    }

    @Test
    public void checkIfEmployeesAreReturnedSearchedAndSorted_ByFullNameAll() {
        List<Employee> mockEmployeeList = List.of(
                new Employee("TestEmp1", "TestEmp1", "TestEmp1", "TestEmp1"),
                new Employee("TestEmp2", "TestEmp2", "TestEmp2", "TestEmp2")
        );

        Map<String, String> searchParams = new TreeMap<>();
        searchParams.put("searchByFirstName", "%test%");
        searchParams.put("searchByLastName", "%test%");
        Pageable pageable = PageRequest.of(0, 100);

        String searchBy = "Full Name";
        String sortBy = "id";
        String sortDirection = "asc";

        String expectedQuery = "SELECT e FROM Employee e " +
                "LEFT JOIN FETCH e.employeeActivities ea " +
                "WHERE (:onlyActive=false OR ea.active=true) " +
                "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee = e) " +
                "AND LOWER(e.firstName) LIKE LOWER(:firstSearchParam) " +
                "AND LOWER(e.lastName) LIKE LOWER(:secondSearchParam) " +
                "ORDER BY ea.active DESC, e.id ASC";

        when(employeeDao.executeQueryWithSearchingAndSorting(anyString(), anyInt(), anyString(), anyString(), anyBoolean())).thenReturn(mockEmployeeList);

        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> paramCountCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> firstParamCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> secondParamCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> onlyActiveCaptor = ArgumentCaptor.forClass(Boolean.class);

        employeeService.findAllSearchedAndSortedWithActivities(searchBy, searchParams, sortBy, sortDirection, pageable, false);

        verify(employeeDao).executeQueryWithSearchingAndSorting(
                queryCaptor.capture(),
                paramCountCaptor.capture(),
                firstParamCaptor.capture(),
                secondParamCaptor.capture(),
                onlyActiveCaptor.capture()
        );

        assertEquals(expectedQuery, queryCaptor.getValue());
        assertEquals(2, paramCountCaptor.getValue());
        assertEquals("%test%", firstParamCaptor.getValue());
        assertEquals("%test%", secondParamCaptor.getValue());
        assertFalse(onlyActiveCaptor.getValue());
    }
}

