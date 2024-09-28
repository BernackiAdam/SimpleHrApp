package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.entity.Project;
import com.bernacki.hrapp.entity.ProjectPhase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@TestPropertySource(locations = "/test.properties")
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEach(){
        jdbcTemplate.execute("ALTER TABLE projects ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE clients ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE project_phase ALTER COLUMN id RESTART WITH 1");

        jdbcTemplate.execute("INSERT INTO clients (name, address) VALUES('Client1', 'AddressClient1')");
        jdbcTemplate.execute("INSERT INTO projects (title, project_type, description, client_id, active) " +
                "VALUES('Proj1', 'MOBILE_APP', 'description1', 1, true)");
        jdbcTemplate.execute("INSERT INTO project_phase (project_id, phase, date) VALUES (1, 'STARTING_PHASE', '2024-01-01')");
    }

    @AfterEach
    public void afterEach(){
        jdbcTemplate.execute("DELETE FROM project_phase");
        jdbcTemplate.execute("DELETE FROM projects");
        jdbcTemplate.execute("DELETE FROM clients");
    }

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
        jdbcTemplate.execute("INSERT INTO project_phase (project_id, phase, date) VALUES (1, 'CURRENT_PHASE', '2024-02-02')");
        List<Project> projects = projectRepository.findAll();
        for (Project proj : projects){
            assertEquals(2, proj.getPhases().size(), "Should be 2 phases");
        }
    }

    @Test
    public void checkIfProjectsAssignedToClientsAreLoaded(){
        List<Project> projects = projectRepository.findProjectAssignedToClient(1);
        assertFalse(projects.isEmpty(), "Projects should not be empty");
        assertEquals(1, projects.size(), "There should be ony one project");
        assertEquals("Proj1", projects.stream()
                .map(Project::getTitle)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Project list is empty")));
    }

    @Test
    public void checkIfProjectsAreLoadedByTitleLike(){
        Pageable pageable = PageRequest.of(0, 100);
        String searchPattern = "%" + "proj" + "%";
        Page<Project> projects = projectRepository.findByTitleLikeIgnoreCase(searchPattern, pageable);
        assertFalse(projects.isEmpty());
    }

    @Test
    public void checkIfProjectsAreLoadedByType(){
        String projectType = "MOBILE_APP";
        Pageable pageable = PageRequest.of(0, 100);
        Page<Project> projects = projectRepository.findByProjectType(projectType, pageable);
        assertFalse(projects.isEmpty());
    }

    @Test
    public void checkIfProjectsAreLoadedByCurrPhase(){
        jdbcTemplate.execute("INSERT INTO project_phase (project_id, phase, date) VALUES (1, 'CURRENT_PHASE', '2024-02-02')");
        String currPhase = "CURRENT_PHASE";
        Pageable pageable = PageRequest.of(0, 100);
        Page<Project> projects = projectRepository.findByCurrentPhase(currPhase, pageable);
        assertFalse(projects.isEmpty());

    }

    @Test
    public void checkIfProjectsAreSaved(){
        List<Project> projects1 = projectRepository.findAll();
        assertEquals(1, projects1.size(), "There should be only one project");
        Project projectToSave = new Project("Project2", "Description");
        Project project = new Project("Proj2", "Description2");
        projectRepository.save(project);
        List<Project> projects2 = projectRepository.findAll();
        assertEquals(2, projects2.size(), "There should be two projects");
    }

    @Test
    public void checkIfProjectIsUpdated(){
        Optional<Project> result = projectRepository.findById(1);
        Project project = result.orElse(null);
        assertNotNull(project);
        assertEquals("description1", project.getDescription(), "Project descr is not Description1");
        String updatedDescription = "Modified description1";
        project.setDescription(updatedDescription);
        projectRepository.save(project);
        Optional<Project> resultAfterUpdate = projectRepository.findById(1);
        Project projectAfterUpdate = resultAfterUpdate.orElse(null);
        assert projectAfterUpdate != null;
        assertEquals(updatedDescription, projectAfterUpdate.getDescription(), "Description should be updated");
    }
}
