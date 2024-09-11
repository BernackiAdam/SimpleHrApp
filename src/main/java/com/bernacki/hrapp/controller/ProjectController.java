package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectAssignment;
import com.bernacki.hrapp.model.ProjectPhase;
import com.bernacki.hrapp.service.ProjectAssignmentService;
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

    @Autowired
    private ProjectAssignmentService projectAssignmentService;


    @GetMapping("/list")
    public String getProjectsList(Model model){
        List<Project> projectList = projectService.findAll();
        model.addAttribute("projects", projectList);
        return "project/project-list";
    }

    @GetMapping("/info")
    public String projectInfo(@RequestParam("projectId") int id,
                              Model model){
        Project project = projectService.findById(id);
        List<ProjectAssignment> assignments = projectAssignmentService.findEmployeesAssignedToProjectWithRolesById(id);
        List<ProjectPhase> phases = projectService.findPhasesAssignedToProject(id);
        model.addAttribute("project", project);
        model.addAttribute("assignments", assignments);
        model.addAttribute("phases", phases);
        return "project/project-info";
    }
}
