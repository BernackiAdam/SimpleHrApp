package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectPhase;
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
    public List<ProjectPhase> findPhasesAssignedToProject(int id) {
        TypedQuery<ProjectPhase> query = entityManager.createQuery(
                "SELECT pp FROM ProjectPhase pp LEFT JOIN pp.project p WHERE p.id=:projectId", ProjectPhase.class
        );
        query.setParameter("projectId", id);

        return query.getResultList().reversed();
    }

}
