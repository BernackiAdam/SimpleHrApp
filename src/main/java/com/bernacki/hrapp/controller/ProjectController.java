package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/list")
    public String getProjectsList(Model model){
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "project/project-list";
    }

    @GetMapping("/info")
    public String projectInfo(@RequestParam("projectId") int id,
                              Model model){
        Project project = projectService.findById(id);
        List<Employee> employees = projectService.findEmployeesAssignedToProject(id);
        model.addAttribute("project", project);
        model.addAttribute("employees", employees);
        return "project/project-info";
    }
}
