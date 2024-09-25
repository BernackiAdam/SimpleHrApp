package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.ProjectAssignment;
import com.bernacki.hrapp.model.ProjectAssignmentId;
import com.bernacki.hrapp.service.EmployeeService;
import com.bernacki.hrapp.service.ProjectAssignmentService;
import com.bernacki.hrapp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/assignment")
public class ProjectAssignmentController {

    @Autowired
    private ProjectAssignmentService assignmentService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    @Value("${searchByListAssignment}")
    private List<String> searchByList;

    @Value("${seniority}")
    private List<String> seniorityList;

    @Value("${position}")
    private List<String> positionList;

    @Value("${projectRoles}")
    private List<String> projectRoles;

    @GetMapping("/assign-employee")
    public String assignEmployeeToProject(
//            @RequestParam Map<String, String> idRoleMap,
            @RequestParam(value="newEmployeeRole", defaultValue = "") String role,
            @RequestParam(value = "employeeId", defaultValue = "0") int employeeId,
            @RequestParam Map<String, String> searchParams,
            @RequestParam(value = "projectId") int projectId,
            @RequestParam(value = "searchBy", defaultValue = "") String searchBy,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "onlyActiveEmployees", defaultValue = "false") Boolean onlyActiveEmployees,

            Model model
            ){

        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Employee> employeePage = employeeService.getEmployeeListSearched(searchBy, searchParams, pageable, onlyActiveEmployees);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, employeePage.getTotalPages()).boxed().toList();

        model.addAttribute("employeePage", employeePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("searchBy", searchBy);
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("newEmployeeRole", role);
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("projectId", projectId);

        model.addAttribute("projectRoles", projectRoles);
        model.addAttribute("searchByList", searchByList);
        model.addAttribute("seniorityList", seniorityList);
        model.addAttribute("positionList", positionList);

        return "manage/assign-employees-to-project";
    }

    @PostMapping("/save")
    public String saveAssignment(@RequestParam(name = "employeeId", defaultValue = "0") int employeeId,
                                 @RequestParam(name= "newEmployeeRole", defaultValue = "") String employeeRole,
                                 @RequestParam(name = "projectId") int projectId,
                                 Model model){


        ProjectAssignment projectAssignment = new ProjectAssignment();
        projectAssignment.setRole(employeeRole);
        projectAssignment.setProject(projectService.findById(projectId));
        projectAssignment.setEmployee(employeeService.findById(employeeId));

        ProjectAssignmentId projectAssignmentId = new ProjectAssignmentId(employeeId, projectId);
        projectAssignment.setId(projectAssignmentId);

        assignmentService.save(projectAssignment);

        return "redirect:/project/info?projectId=" + projectId;
    }
}
