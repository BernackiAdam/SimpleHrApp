package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectPhase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ProjectServiceTest extends DaoBaseTestClass{

    @Autowired
    private ProjectRepository projectRepository;

//    @Autowired
//    private Project project;
//
//    @Autowired
//    private Employee employee;

    @Test
    public void checkIfProjectsAreLoaded(){
        List<Project> projects = projectRepository.findAll();
        assertFalse(projects.isEmpty(), "Project list should not be empty");
    }

    @Test
    public void checkIfProjectByIdIsLoaded(){
        Optional<Project> result = projectRepository.findById(1);
        Project project = null;
        if(result.isPresent()){
            project = result.get();
        }
        assertFalse(project.getTitle().isEmpty(), "Project title should not be empty");
    }

    @Test
    public void checkIfProjectsPhasesAreLoaded(){
        List<ProjectPhase> phases = projectRepository.findPhasesAssignedToProject(1);
        assertFalse(phases.isEmpty(), "Phases should not be empty");
    }


    @Test
    public void checkIfProjectsAreLoadedWithPhases(){
        List<Project> projects = projectRepository.findAll();
        for (Project proj : projects){
            assertEquals(3, proj.getPhases().size(), "Should be 3 phases");
        }
    }

    @Test
    public void checkIfProjectsAssignedToClientsAreLoaded(){
        List<Project> projects = projectRepository.findProjectAssignedToClient(1);
        assertFalse(projects.isEmpty(), "Projects should not be empty");
        assertEquals(1, projects.size(), "There should be ony one project");
        assertEquals("Project3", projects.stream()
                .map(Project::getTitle)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Project list is empty")));
    }

//    @Test
//    public void checkIfProjectsWithoutConsultantAreLoading(){
//        List<Project> projects = projectRepository.findProjectsWithoutConsultant();
//        assertEquals(1, projects.size());
//        assertEquals("ProjWithoutCons", projects.stream().map(Project::getTitle).findFirst().orElseThrow(() -> new IllegalArgumentException("No project found")));
//    }
}
