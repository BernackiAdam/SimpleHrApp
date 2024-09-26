package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.EmployeeActivity;
import com.bernacki.hrapp.model.ProjectAssignment;
import com.bernacki.hrapp.service.EmployeeActivityService;
import com.bernacki.hrapp.service.EmployeeService;
import com.bernacki.hrapp.service.ProjectAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private EmployeeActivityService employeeActivityService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ProjectAssignmentService projectAssignmentService, EmployeeActivityService employeeActivityService) {
        this.employeeService = employeeService;
        this.projectAssignmentService = projectAssignmentService;
        this.employeeActivityService = employeeActivityService;
    }

    @Value("${searchByListEmployee}")
    private List<String> searchByList;

    @Value("${seniority}")
    private List<String> seniorityList;

    @Value("${position}")
    private List<String> positionList;

    @GetMapping("/list")
    public String getEmployeeList(
            @RequestParam(value = "searchBy", required = false, defaultValue = "id") String searchBy,
            @RequestParam Map<String, String> searchParams,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            @RequestParam(value = "onlyActive", defaultValue = "false") Boolean onlyActive,
            Model model){

        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeService.findAllSearchedAndSortedWithActivities(searchBy, searchParams, sortBy, sortDirection, pageable, onlyActive);

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
        model.addAttribute("onlyActive", onlyActive);

        model.addAttribute("searchByList", searchByList);
        model.addAttribute("seniorityList", seniorityList);
        model.addAttribute("positionList", positionList);
        return "employee/employee-list";
    }

    @GetMapping("/info")
    public String showUserInfo(@RequestParam("employeeId") int id,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "5") int size,
                               Model model){
        Employee employee = employeeService.findById(id);

        Pageable pageable = PageRequest.of(page, size);
//        Page<EmployeeActivity> employeeActivities = employeeActivityService.findActivitiesByEmployeeId(id, pageable);
        Page<EmployeeActivity> employeeActivities = employeeActivityService.findActivitiesByEmployeeIdReversed(id, pageable);
        List<ProjectAssignment> assignments = projectAssignmentService.findProjectsAssignedToEmployeeWithRolesById(id);
        model.addAttribute("assignments", assignments);
        model.addAttribute("employee", employee);
        model.addAttribute("employeeActivities", employeeActivities);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "employee/employee-info";
    }
}
