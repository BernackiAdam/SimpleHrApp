package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectAssignment;
import com.bernacki.hrapp.model.ProjectPhase;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectAssignmentService projectAssignmentService;

    @Value("${searchByListProject}")
    List<String> searchByList;

    @Value("${projectType}")
    List<String> projectTypes;

    @Value("${phase}")
    List<String> phases;

    private Page<Project> getProjectPage(Map<String,String> searchParams, String searchBy, Pageable pageable){
        Page<Project> projectPage = null;
        String searchByTitle = searchParams.get("searchByTitle");
        String searchByProjectType = searchParams.get("searchByProjectType");
        String searchByCurrentPhase = searchParams.get("searchByCurrentPhase");

        if(searchBy==null || searchBy.isEmpty()){
            projectPage = projectService.findAllPaginated(pageable);
        } else if (searchBy.equals("Title")) {
            projectPage = projectService.findByTitle(searchByTitle, pageable);
        } else if (searchBy.equals("Type")) {
            projectPage = projectService.findByProjectType(searchByProjectType, pageable);
        } else if (searchBy.equals("Current Phase")) {
            projectPage = projectService.findByCurrentPhase(searchByCurrentPhase, pageable);
        }
        else {
            projectPage = projectService.findAllPaginated(pageable);
        }

        return projectPage;
    }

    @GetMapping("/list")
    public String getProjectsList(
            @RequestParam Map<String, String> searchParams,
            @RequestParam(value = "searchBy", defaultValue = "") String searchBy,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model){
        Sort sort = sortDirection.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size, sort);
        Page<Project> projectPage = getProjectPage(searchParams, searchBy, pageable);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, projectPage.getTotalPages())
                        .boxed().toList();
        model.addAttribute("projectPage", projectPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", projectPage.getTotalPages());
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("searchByList", searchByList);
        model.addAttribute("projectTypes", projectTypes);
        model.addAttribute("phases", phases);

        model.addAttribute("searchBy", searchBy);
        model.addAttribute("searchParams", searchParams);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortBy", sortBy);
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
