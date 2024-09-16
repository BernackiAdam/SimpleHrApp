package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.ProjectAssignment;
import com.bernacki.hrapp.service.EmployeeService;
import com.bernacki.hrapp.service.ProjectAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private ProjectAssignmentService projectAssignmentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ProjectAssignmentService projectAssignmentService) {
        this.employeeService = employeeService;
        this.projectAssignmentService = projectAssignmentService;
    }

//    @GetMapping("/list")
//    public String getEmployeeList(Model model){
//        model.addAttribute( "employees",employeeService.findAll());
//        return "employee-list";
//    }

    @GetMapping("/list")
    public String getEmployeeList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model){
        Page<Employee> employeePage = employeeService.findAllPaginated(page, size);

        List<Integer> pageNumbers = IntStream.rangeClosed(1, employeePage.getTotalPages())
                        .boxed().toList();
        model.addAttribute("employeePage", employeePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers);
        return "employee/employee-list";
    }

    @GetMapping("/info")
    public String showUserInfo(@RequestParam("employeeId") int id, Model model){
        Employee employee = employeeService.findById(id);
        List<ProjectAssignment> assignments = projectAssignmentService.findProjectsAssignedToEmployeeWithRolesById(id);
        model.addAttribute("assignments", assignments);
        model.addAttribute("employee", employee);
        return "employee/employee-info";
    }
}
