package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.dto.ProjectConsultantDto;
import com.bernacki.hrapp.dto.ProjectDto;
import com.bernacki.hrapp.entity.Client;
import com.bernacki.hrapp.entity.Project;
import com.bernacki.hrapp.entity.ProjectConsultant;
import com.bernacki.hrapp.entity.ProjectPhase;
import com.bernacki.hrapp.service.ClientService;
import com.bernacki.hrapp.service.ProjectConsultantService;
import com.bernacki.hrapp.service.ProjectPhaseService;
import com.bernacki.hrapp.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manage/project")
public class ProjectManageController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectConsultantService consultantService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProjectPhaseService projectPhaseService;


    @InitBinder
    private void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, editor);
    }

    @Value("${projectType}")
    private List<String> projectTypes;

    @Value("${phase}")
    private List<String> phases;

    private void populateModel(Model model){
        model.addAttribute("clientList", clientService.findAll());
        model.addAttribute("phases", phases);
        model.addAttribute("projectTypes", projectTypes);
    }

    @GetMapping("/add")
    public String addProject(Model model){
        populateModel(model);
        model.addAttribute("project", new ProjectDto());
        model.addAttribute("consultant", new ProjectConsultantDto());
        return "/manage/project-add-form";
    }

    @PostMapping("/save")
    public String saveProject(@Valid @ModelAttribute("project") ProjectDto projectDto,
                              BindingResult bindingResultProject,
                              @ModelAttribute("consultant") ProjectConsultantDto projectConsultantDto,
                              BindingResult bindingResultConsultant,
                              @RequestParam(name = "addConsultant", required = false, defaultValue = "false") boolean addConsultant,
                              Model model){
        if (addConsultant) {
            if (projectConsultantDto.getEmail() ==null) {
                bindingResultConsultant.addError(new FieldError("consultant", "email", "Email is required"));
            }
            if (projectConsultantDto.getTelephoneNumber() ==null) {
                bindingResultConsultant.addError(new FieldError("consultant", "telephoneNumber", "Telephone number is required"));
            }
            if (projectConsultantDto.getFirstName() ==null) {
                bindingResultConsultant.addError(new FieldError("consultant", "firstName", "First name is required"));
            }
            if (projectConsultantDto.getLastName() ==null) {
                bindingResultConsultant.addError(new FieldError("consultant", "lastName", "Last name is required"));
            }
            if(bindingResultConsultant.hasErrors()){
                populateModel(model);
                return "/manage/project-add-form";
            }
        }

        if(bindingResultProject.hasErrors()){
            populateModel(model);
            return "/manage/project-add-form";
        }

        Client client = clientService.findById(projectDto.getClientId());
        Project project = new Project();
        project.setTitle(projectDto.getTitle());
        project.setProjectType(projectDto.getProjectType());
        project.setDescription(projectDto.getDescription());
        project.setClient(client);
        if(addConsultant){
            ProjectConsultant projectConsultant = new ProjectConsultant();
            projectConsultant.setFirstName(projectConsultantDto.getFirstName());
            projectConsultant.setLastName(projectConsultantDto.getLastName());
            projectConsultant.setEmail(projectConsultantDto.getEmail());
            projectConsultant.setTelephoneNumber(projectConsultantDto.getTelephoneNumber());
            project.setProjectConsultant(projectConsultant);
            projectConsultant.setProject(project);
        }
        ProjectPhase projectPhase = new ProjectPhase();
        projectPhase.setPhase(projectDto.getStartingPhase());
        projectPhase.setProject(project);
        project.setPhases(List.of(projectPhase));

        projectService.save(project);
        return "redirect:/manage/";
    }

    @GetMapping("/delete")
    public String deleteProjectById(int projectId){
        projectService.deleteById(projectId);
        return "redirect:/project/list";
    }

    @PostMapping("/add-phase")
    public String addPhaseToProject(@RequestParam("projectId") int projectId,
                                    @RequestParam("phaseName") String phaseName){
        ProjectPhase projectPhase = new ProjectPhase();
        projectPhase.setPhase(phaseName);
        projectPhase.setProject(projectService.findById(projectId));
        projectPhaseService.save(projectPhase);
        return "redirect:/project/info?projectId="+projectId;
    }
}
