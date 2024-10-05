package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.dto.EmployeeDto;
import com.bernacki.hrapp.entity.Employee;
import com.bernacki.hrapp.service.EmployeeActivityService;
import com.bernacki.hrapp.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeManageController.class)
public class EmployeeManageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeActivityService employeeActivityService;


    @Test
    public void testSaveEmployee_newEmployee_redirect() throws Exception{

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("TestFirstName");
        employeeDto.setLastName("TestLastName");
        employeeDto.setEmail("TestEmail@email.com");
        employeeDto.setTelephoneNumber("123123123");
        employeeDto.setSeniority("TestSeniority");
        employeeDto.setPosition("TestPosition");


        mockMvc.perform(post("/manage/employee/save")
                .param("firstName", employeeDto.getFirstName())
                .param("lastName", employeeDto.getLastName())
                .param("email", employeeDto.getEmail())
                .param("telephoneNumber", employeeDto.getTelephoneNumber())
                .param("seniority", employeeDto.getSeniority())
                .param("position", employeeDto.getPosition()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/manage/"));
        verify(employeeService, times(1))
                .saveByEmployeeDto(any(EmployeeDto.class));
    }

    @Test
    public void testSaveEmployee_newEmployee_hasErrors() throws Exception{

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("");
        employeeDto.setLastName("");
        employeeDto.setEmail("");
        employeeDto.setTelephoneNumber("");
        employeeDto.setSeniority("TestSeniority");
        employeeDto.setPosition("TestPosition");


        mockMvc.perform(post("/manage/employee/save")
                        .param("firstName", employeeDto.getFirstName())
                        .param("lastName", employeeDto.getLastName())
                        .param("email", employeeDto.getEmail())
                        .param("telephoneNumber", employeeDto.getTelephoneNumber())
                        .param("seniority", employeeDto.getSeniority())
                        .param("position", employeeDto.getPosition()))
                .andExpect(status().isOk())
                .andExpect(view().name("manage/employee-add-form"))
                .andExpect(model().attributeHasFieldErrors("employee","firstName",
                        "lastName", "email", "telephoneNumber"));
        verify(employeeService, times(0))
                .saveByEmployeeDto(any(EmployeeDto.class));
    }

    @Test
    public void testSaveEmployee_existingEmployee_redirect() throws Exception{

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1);
        employeeDto.setFirstName("TestFirstName");
        employeeDto.setLastName("TestLastName");
        employeeDto.setEmail("TestEmail@email.com");
        employeeDto.setTelephoneNumber("123123123");
        employeeDto.setSeniority("TestSeniority");
        employeeDto.setPosition("TestPosition");

        Employee existingEmployee = new Employee();
        existingEmployee.setFirstName("ExistingEmpName");

        mockMvc.perform(post("/manage/employee/save")
                        .param("id", Integer.toString(employeeDto.getId()))
                        .param("firstName", employeeDto.getFirstName())
                        .param("lastName", employeeDto.getLastName())
                        .param("email", employeeDto.getEmail())
                        .param("telephoneNumber", employeeDto.getTelephoneNumber())
                        .param("seniority", employeeDto.getSeniority())
                        .param("position", employeeDto.getPosition()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/manage/"));
        verify(employeeService, times(1))
                .saveByEmployeeDto(any(EmployeeDto.class));

    }
}
