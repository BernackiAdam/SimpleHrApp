package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDao{

    private EntityManager entityManager;

    @Autowired
    public ProjectDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Project> findAll() {
        TypedQuery<Project> query = entityManager.createQuery(
                "From Project", Project.class
        );
        return query.getResultList();
    }

    @Override
    public Project findById(int id) {
        return entityManager.find(Project.class, id);
    }

    @Override
    public Project findByName(String name) {
        return entityManager.find(Project.class, name);
    }

    @Override
    public List<Employee> findEmployeesAssignedToProject(int id) {
        TypedQuery<Employee> query = entityManager.createQuery(
                "SELECT e FROM Employee e LEFT JOIN FETCH e.projects p WHERE p.id=:projectId", Employee.class
        );
        query.setParameter("projectId", id);
        return query.getResultList();
    }
}
