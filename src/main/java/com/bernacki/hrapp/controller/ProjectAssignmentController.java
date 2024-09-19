package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.service.EmployeeService;
import com.bernacki.hrapp.service.ProjectAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@RequestMapping("/project/assignment")
public class ProjectAssignmentController {

    @Autowired
    private ProjectAssignmentService assignmentService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/assign-employee")
    public String assignEmployeeToProject(
            @RequestParam Map<String, String> idsAndRoles,
            @RequestParam Map<String, String> searchParams,
            @RequestParam(value = "searchBy", defaultValue = "") String searchBy,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model
            ){

        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employeePage = employeeService.getEmployeeListSearched(searchBy, searchParams, pageable);

        return "assign-employees-to-project";
    }
}
