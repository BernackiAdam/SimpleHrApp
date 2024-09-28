package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.dto.ProjectConsultantDto;
import com.bernacki.hrapp.entity.Project;
import com.bernacki.hrapp.entity.ProjectConsultant;
import com.bernacki.hrapp.service.ProjectConsultantService;
import com.bernacki.hrapp.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manage/consultant")
public class ConsultantManageController {

    @Autowired
    private ProjectConsultantService consultantService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/add")
    public String addConsultant(@RequestParam("projectId") int id,
                                Model model){
//        model.addAttribute("projectId", id);
        ProjectConsultantDto consultant = new ProjectConsultantDto();
        consultant.setProjectId(id);
        model.addAttribute("consultant", consultant);
        return "manage/consultant-add-form";
    }

    @PostMapping("/save")
    public String saveConsultant(@Valid @ModelAttribute("consultant") ProjectConsultantDto consultantDto,
                                 BindingResult bindingResult,
//                                 @RequestParam("projectId") int id,
                                 Model model){
        if(bindingResult.hasErrors()){
//            model.addAttribute("projectId", id);
            return "manage/consultant-add-form";
        }
        ProjectConsultant consultant = new ProjectConsultant();
        Project project = projectService.findById(consultantDto.getProjectId());
        consultant.setProject(project);
        consultant.setFirstName(consultantDto.getFirstName());
        consultant.setLastName(consultantDto.getLastName());
        consultant.setEmail(consultantDto.getEmail());
        consultant.setTelephoneNumber(consultantDto.getTelephoneNumber());
        consultantService.save(consultant);
        return "redirect:/project/info?projectId=" + consultantDto.getProjectId();
    }
}
