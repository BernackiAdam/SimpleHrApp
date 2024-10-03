package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.dto.ProjectConsultantDto;
import com.bernacki.hrapp.entity.Project;
import com.bernacki.hrapp.service.ProjectConsultantService;
import com.bernacki.hrapp.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultantManageController.class)
public class ConsultantManageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private ProjectConsultantService projectConsultantService;

    @Test
    public void testSaveConsultant_validInput_Redirect() throws Exception{
        Project project = new Project();
        project.setId(1);

        ProjectConsultantDto projectConsultantDto = new ProjectConsultantDto();
        projectConsultantDto.setProjectId(1);
        projectConsultantDto.setFirstName("TestFirstName");
        projectConsultantDto.setLastName("TestLastName");
        projectConsultantDto.setEmail("testEmail@email.com");
        projectConsultantDto.setTelephoneNumber("123123123");

        when(projectService.findById(1)).thenReturn(project);

        mockMvc.perform(post("/manage/consultant/save")
                .param("projectId", Integer.toString(projectConsultantDto.getProjectId()))
                .param("firstName", projectConsultantDto.getFirstName())
                .param("lastName", projectConsultantDto.getLastName())
                .param("email", projectConsultantDto.getEmail())
                .param("telephoneNumber", projectConsultantDto.getTelephoneNumber()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/info?projectId=1"));

        verify(projectConsultantService, times(1))
                .saveUsingDto(any(ProjectConsultantDto.class), eq(project));
    }

    @Test
    public void testSaveConsultant_hasErrors() throws Exception{

        mockMvc.perform(post("/manage/consultant/save")
                        .param("projectId", "1")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("email", "")
                        .param("telephoneNumber", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("manage/consultant-add-form"))
                .andExpect(model().attributeHasFieldErrors("consultant"
                        , "firstName", "lastName", "email", "telephoneNumber"));
        verify(projectConsultantService, times(0)).saveUsingDto(any(ProjectConsultantDto.class), any(Project.class));
    }
}
