package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.ProjectAssignment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectAssignmentDaoImpl implements ProjectAssignmentDao{

    private EntityManager entityManager;

    @Autowired
    public ProjectAssignmentDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ProjectAssignment> findProjectsAssignedToEmployeeWithRolesById(int id) {
        TypedQuery<ProjectAssignment> query = entityManager.createQuery(
                "SELECT pa FROM ProjectAssignment pa WHERE pa.employee.id =: employeeId", ProjectAssignment.class
        );
        query.setParameter("employeeId", id);
        return query.getResultList();
    }

    @Override
    public List<ProjectAssignment> findEmployeesAssignedToProjectWithRolesById(int id) {
        TypedQuery<ProjectAssignment> query = entityManager.createQuery(
                "SELECT pa FROM ProjectAssignment pa WHERE pa.project.id =: projectId", ProjectAssignment.class
        );
        query.setParameter("projectId", id);
        return query.getResultList();
    }
}
