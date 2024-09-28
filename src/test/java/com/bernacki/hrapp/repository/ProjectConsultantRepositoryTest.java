package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.entity.ProjectConsultant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "/test.properties")
public class ProjectConsultantRepositoryTest {

    @Autowired
    private ProjectConsultantRepository projectConsultantRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    public void beforeEach(){
        jdbcTemplate.execute("ALTER TABLE projects ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE project_consultant ALTER COLUMN id RESTART WITH 1");


        jdbcTemplate.execute("INSERT INTO projects (title, project_type, description, active) " +
                "VALUES('TestProj1', 'TestType', 'TestDesc', true)");
        jdbcTemplate.execute("INSERT INTO projects (title, project_type, description, active) " +
                "VALUES('TestProj2', 'TestType', 'TestDesc', true)");
        jdbcTemplate.execute("INSERT INTO project_consultant (first_name, last_name, email, tel_nr, project_id) " +
                "VALUES('TestConsFirstName1', 'TestConsLastName1', 'TestEmail1', 'testTel1', 1)");
        jdbcTemplate.execute("INSERT INTO project_consultant (first_name, last_name, email, tel_nr, project_id) " +
                "VALUES('TestConsFirstName2', 'TestConsLastName2', 'TestEmail2', 'testTel2', 2)");
    }

    @AfterEach
    public void afterEach(){
        jdbcTemplate.execute("DELETE FROM project_consultant");
        jdbcTemplate.execute("DELETE FROM projects");
    }

    @Test
    public void checkIfProjectConsultantsAreLoaded(){
        List<ProjectConsultant> projectConsultants = projectConsultantRepository.findAll();
        assertFalse(projectConsultants.isEmpty());
        assertEquals(2, projectConsultants.size());
    }

    @Test
    public void checkIfProjectConsultantIsLoadedByProjectId(){
        ProjectConsultant projectConsultant = projectConsultantRepository.findByProjectId(1);
        assertNotNull(projectConsultant);
        assertEquals("TestProj1", projectConsultant.getProject().getTitle());
    }

}
