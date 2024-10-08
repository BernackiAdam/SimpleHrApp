package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.entity.Employee;
import com.bernacki.hrapp.entity.Project;
import com.bernacki.hrapp.entity.ProjectAssignment;
import com.bernacki.hrapp.entity.ProjectAssignmentId;
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
public class ProjectAssignmentTest {

    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEach(){
        jdbcTemplate.execute("ALTER TABLE projects ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("ALTER TABLE employee ALTER COLUMN id RESTART WITH 1");

        jdbcTemplate.execute("INSERT INTO employee (first_name, last_name, email, tel_nr, seniority, position) " +
                "VALUES('TestName1', 'TestSurname1', 'test1@email.com', '123123123', 'Junior', 'Backend Developer')");
        jdbcTemplate.execute("INSERT INTO employee (first_name, last_name, email, tel_nr, seniority, position) " +
                "VALUES('TestName2', 'TestSurname2', 'test2@email.com', '123123123', 'Junior', 'Backend Developer')");
        jdbcTemplate.execute("INSERT INTO employee (first_name, last_name, email, tel_nr, seniority, position) " +
                "VALUES('TestName3', 'TestSurname3', 'test3@email.com', '123123123', 'Junior', 'Backend Developer')");

        jdbcTemplate.execute("INSERT INTO projects (title, project_type, description, active) " +
                "VALUES('Proj1', 'MOBILE_APP', 'description1', true)");
        jdbcTemplate.execute("INSERT INTO projects (title, project_type, description, active) " +
                "VALUES('Proj2', 'MOBILE_APP', 'description1', true)");

        jdbcTemplate.execute("INSERT INTO projects_employees VALUES(1,1, 'project1_role1')");
        jdbcTemplate.execute("INSERT INTO projects_employees VALUES(2,1, 'project1_role2')");
        jdbcTemplate.execute("INSERT INTO projects_employees VALUES(2,2, 'project2_role1')");
        jdbcTemplate.execute("INSERT INTO projects_employees VALUES(3,2, 'project2_role2')");

    }

    @AfterEach
    public void afterEach(){
        jdbcTemplate.execute("DELETE FROM projects_employees");
        jdbcTemplate.execute("DELETE FROM employee");
        jdbcTemplate.execute("DELETE FROM projects");
    }
    @Test
    public void checkIfEmployeesAssignedToProjAreLoadedById(){
        List<ProjectAssignment> assignments = projectAssignmentRepository.findEmployeesAssignedToProjectWithRolesById(1);
        for(ProjectAssignment assign : assignments){
            assertFalse(assign.getEmployee().getFirstName().isEmpty(), "Employee name should not be empty");
            assertFalse(assign.getRole().isEmpty(), "Role should not be empty");
        }
    }

    @Test
    public void checkIfProjectsAssignedToEmpAreLoadedById(){
        List<ProjectAssignment> assignments = projectAssignmentRepository.findProjectsAssignedToEmployeeWithRolesById(1);
        for(ProjectAssignment assign : assignments){
            assertFalse(assign.getProject().getTitle().isEmpty(), "Project title should not be empty");
            assertFalse(assign.getRole().isEmpty(), "Role should not be empty");
        }
    }

    @Test
    public void checkIfProjectAssignmentIsLoadedByEmployeeIdAndProjectId(){
        ProjectAssignment assignment = projectAssignmentRepository.findByEmployeeIdAndProjectId(1,1);
        assertEquals("project1_role1", assignment.getRole(), "Role should be project1_role1");
    }

    @Test
    public void checkIfProjectAssignmentIsSaved(){

        ProjectAssignment projectAssignment1 = projectAssignmentRepository.findByEmployeeIdAndProjectId(1,1);
        ProjectAssignment projectAssignment2 = projectAssignmentRepository.findByEmployeeIdAndProjectId(3,2);
        assertNotNull(projectAssignment1);
        assertNotNull(projectAssignment2);
        Employee employee = projectAssignment1.getEmployee();
        Project project = projectAssignment2.getProject();

        ProjectAssignmentId projectAssignmentId = new ProjectAssignmentId(employee.getId(), project.getId());
        ProjectAssignment projectAssignment = new ProjectAssignment(projectAssignmentId,employee, project, "project2_role3");

        projectAssignmentRepository.save(projectAssignment);
        ProjectAssignment projectAssignmentAfterSave = projectAssignmentRepository.findByEmployeeIdAndProjectId(1, 2);
        assertNotNull(projectAssignmentAfterSave);
    }

    @Test
    public void checkIfProjectAssignmentIdsAreLoadedByEmployeeId(){
        List<ProjectAssignmentId> projectAssignmentIdList = projectAssignmentRepository.findProjectAssignmentIdsByEmployeeId(2);
        assertFalse(projectAssignmentIdList.isEmpty());
        assertEquals(2, projectAssignmentIdList.size(), "List should contain 2 items");
    }

    @Test

    public void checkIfProjectAssignmentIdsAreLoadedByProjectId(){
        List<ProjectAssignmentId> projectAssignmentIdList = projectAssignmentRepository.findProjectAssignmentIdsByProjectId(1);
        assertFalse(projectAssignmentIdList.isEmpty());
        assertEquals(2, projectAssignmentIdList.size(), "List should contain 2 items");
    }

    @Test
    public void checkIfProjectAssignmentIsUpdated(){
        ProjectAssignment projectAssignment = projectAssignmentRepository.findByEmployeeIdAndProjectId(1, 1);
        assertEquals("project1_role1", projectAssignment.getRole());
        projectAssignment.setRole("roleAfterUpdate");
        projectAssignmentRepository.save(projectAssignment);
        ProjectAssignment projectAssignmentAfterUpdate = projectAssignmentRepository.findByEmployeeIdAndProjectId(1, 1);

        assertEquals("roleAfterUpdate", projectAssignmentAfterUpdate.getRole(), "Role should be diffrent");
    }

    @Test
    public void checkIfProjectAssignmentsAreDeletedByProjectId(){
        projectAssignmentRepository.deleteProjectAssignmentByProjectId(2);
        List<ProjectAssignment> projectAssignmentList = projectAssignmentRepository.findAll();
        assertFalse(projectAssignmentList.isEmpty());
        assertEquals(2, projectAssignmentList.size(), "List should contain 2 items");
    }
}
