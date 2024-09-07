package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String getEmployeeList(Model model){
        model.addAttribute( "employees",employeeService.findAll());
        return "employee-list";
    }

    @GetMapping("/info")
    public String showUserInfo(@RequestParam("employeeId") int id, Model model){
        Employee employee = employeeService.findById(id);
        List<Project> projects = new ArrayList<>(employeeService.findProjectAssignedToEmployee(id));
        model.addAttribute("projects", projects);
        model.addAttribute("employee", employee);
        return "employee-info";
    }
}
