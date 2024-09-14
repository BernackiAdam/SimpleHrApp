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

    @Test
    public void checkIfProjectsAssignedToClientsAreLoaded(){
        List<Project> projects = projectDao.findProjectAssignedToClient(1);
        assertFalse(projects.isEmpty(), "Projects should not be empty");
        assertEquals(1, projects.size(), "There should be ony one project");
        assertEquals("Project3", projects.stream()
                .map(Project::getTitle)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Project list is empty")));
    }

    @Test
    public void checkIfProjectsWithoutConsultantAreLoading(){
        List<Project> projects = projectDao.findProjectsWithoutConsultant();
        assertEquals(1, projects.size());
        assertEquals("ProjWithoutCons", projects.stream().map(Project::getTitle).findFirst().orElseThrow(() -> new IllegalArgumentException("No project found")));
    }
}
