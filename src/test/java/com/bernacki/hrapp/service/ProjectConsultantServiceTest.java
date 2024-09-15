package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.ProjectConsultant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProjectConsultantServiceTest {

    @Autowired
    private ProjectConsultantService consultantService;

    @Test
    public void checkIfConsultantByProjectIdIsLoaded(){
        ProjectConsultant consultant1 = consultantService.findByProjectId(2);
        assertNotNull(consultant1);
        assertEquals("Halina",consultant1.getFirstName(), "Name should be halina");
    }

    @Test
    public void checkIfConsultantNull(){
        ProjectConsultant consultant = consultantService.findByProjectId(9);
        assertNull(consultant);
    }


}
