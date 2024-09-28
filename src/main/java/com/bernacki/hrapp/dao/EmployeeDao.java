package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.entity.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> executeQueryWithSearchingAndSorting(String query, int paramCount , String firstSearchParam, String secondSearchParam, boolean onlyActive);
}
