package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectPhase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectDaoTest extends DaoBaseTestClass{

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private Project project;

    @Autowired
    private Employee employee;

    @Test
    public void checkIfProjectsAreLoaded(){
        List<Project> projects = projectDao.findAll();
        assertFalse(projects.isEmpty(), "Project list should not be empty");
    }

    @Test
    public void checkIfProjectByIdIsLoaded(){
        Project project1 = projectDao.findById(1);
        assertFalse(project1.getTitle().isEmpty(), "Project title should not be empty");
    }

    @Test
    public void checkIfProjectsPhasesAreLoaded(){
        List<ProjectPhase> phases = projectDao.findPhasesAssignedToProject(1);
        assertFalse(phases.isEmpty(), "Phases should not be empty");
    }


    @Test
    public void checkIfProjectsAreLoadedWithPhases(){
        List<Project> projects = projectDao.findAll();
        for (Project proj : projects){
            assertEquals(3, proj.getPhases().size(), "Should be 3 phases");
        }
    }
}
