package com.bernacki.hrapp.service;

import com.bernacki.hrapp.dao.ProjectDao;
import com.bernacki.hrapp.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    private ProjectDao projectDao;

    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
    }

    @Override
    public Project findById(int id) {
        return projectDao.findById(id);
    }

    @Override
    public Project findByName(String name) {
        return projectDao.findByName(name);
    }
}
