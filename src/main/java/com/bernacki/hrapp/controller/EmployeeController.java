package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.ProjectAssignment;
import com.bernacki.hrapp.service.EmployeeService;
import com.bernacki.hrapp.service.ProjectAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
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

    @Value("${searchByListEmployee}")
    private List<String> searchByList;

    @Value("${seniority}")
    private List<String> seniorityList;

    @Value("${position}")
    private List<String> positionList;

    @GetMapping("/list")
    public String getEmployeeList(
            @RequestParam(value = "searchBy", required = false, defaultValue = "") String searchBy,
            @RequestParam Map<String, String> searchParams,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            Model model){

        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employeePage = employeeService.getEmployeeListSearched(searchBy, searchParams, pageable);

        List<Integer> pageNumbers = IntStream.rangeClosed(1, employeePage.getTotalPages())
                        .boxed().toList();
        model.addAttribute("employeePage", employeePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("searchBy", searchBy);
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);

        model.addAttribute("searchByList", searchByList);
        model.addAttribute("seniorityList", seniorityList);
        model.addAttribute("positionList", positionList);
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
