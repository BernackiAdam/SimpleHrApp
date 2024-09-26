package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Employee> executeQueryWithSearchingAndSorting(String query, int paramCount, String firstSearchParam, String secondSearchParam, boolean onlyActive) {
        Query employeeQuery = entityManager.createQuery(query);
        employeeQuery.setParameter("onlyActive", onlyActive);
        if(paramCount>=1) {
            employeeQuery.setParameter("firstSearchParam", firstSearchParam);
        }
        if(paramCount>=2){
            employeeQuery.setParameter("secondSearchParam", secondSearchParam);
        }

        return employeeQuery.getResultList();
    }
}
