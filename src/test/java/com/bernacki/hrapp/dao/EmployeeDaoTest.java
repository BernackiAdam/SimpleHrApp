package com.bernacki.hrapp.dao;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class EmployeeDaoTest extends DaoBaseTestClass{
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private Project project;

    @Autowired
    private Employee employee;


//    public boolean checkIfManyToManyLazyInit(Class<?> entityClass, String fieldName) throws NoSuchFieldException {
//        Field field = entityClass.getDeclaredField(fieldName);
//        ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);
//
//        return manyToMany.fetch() == FetchType.LAZY;
//    }

    @Test
    public void checkIfEmployeesProjectsAreLoaded() throws NoSuchFieldException {
        Employee employee1 = employeeDao.findById(1);
        Collection<Project> projects;

//        if(checkIfManyToManyLazyInit(Employee.class, "projects")){
            projects= employeeDao.findProjectAssignedToEmployee(1);
//        }else {
//            projects= employee1.getProjects();
//        }
        assertFalse(projects.isEmpty(), "Projects should not be empty");

    }

    @Test
    public void checkIfDataIsCorrect() throws NoSuchFieldException {
        Employee employee1 = employeeDao.findById(1);
        Collection<Project> projects;
//        if(checkIfManyToManyLazyInit(Employee.class, "projects")){
            projects = employeeDao.findProjectAssignedToEmployee(1);
//        }
//        else{
//            projects= employee1.getProjects();
//        }
        String projectName = projects.stream().map(Project::getTitle).findFirst().orElse("none");
        assertEquals("Project1", projectName, "Project name should be equal Project1");
    }
}
