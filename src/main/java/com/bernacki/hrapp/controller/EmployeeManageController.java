package com.bernacki.hrapp.controller;


import com.bernacki.hrapp.dto.EmployeeDto;
import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manage/employee")
public class EmployeeManageController {

    @Value("${seniority}")
    private List<String> seniorityList;

    @Value("${position}")
    private List<String> positionList;

    @Autowired
    private EmployeeService employeeService;

    private void populateModel(Model model){
        model.addAttribute("seniorityList", seniorityList);
        model.addAttribute("positionList", positionList);
    }

    @GetMapping("/add")
    public String addEmployee(Model model){
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employee", employeeDto);
//        model.addAttribute("seniorityList", seniorityList);
//        model.addAttribute("positionList", positionList);
        populateModel(model);
        return "manage/employee-add-form";
    }

    @PostMapping("/save")
    public String saveEmployee(
            @Valid @ModelAttribute("employee") EmployeeDto employeeDto,
            BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            populateModel(model);
            return "manage/employee-add-form";
        }
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setTelephoneNumber(employeeDto.getTelephoneNumber());
        employee.setSeniority(employeeDto.getSeniority());
        employee.setPosition(employeeDto.getPosition());
        employeeService.save(employee);
        return "redirect:/manage/";
    }
}
