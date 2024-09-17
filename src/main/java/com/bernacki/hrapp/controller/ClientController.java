package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.model.Client;
import com.bernacki.hrapp.model.Project;
import com.bernacki.hrapp.model.ProjectConsultant;
import com.bernacki.hrapp.service.ClientService;
import com.bernacki.hrapp.service.ProjectConsultantService;
import com.bernacki.hrapp.service.ProjectService;
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
@RequestMapping("/client")
public class ClientController {

    private ProjectService projectService;
    private ClientService clientService;
    private ProjectConsultantService consultantService;

    @Autowired
    public ClientController(ProjectService projectService, ClientService clientService, ProjectConsultantService consultantService) {
        this.projectService = projectService;
        this.clientService = clientService;
        this.consultantService = consultantService;
    }

    @GetMapping("/list")
    public String getClientList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model){
        Page<Client> clientPage = clientService.findAllPaginated(page, size);
        List<Integer> pageNumbers = IntStream.rangeClosed(1, clientPage.getTotalPages()).boxed().toList();

        model.addAttribute("clientPage", clientPage);
        model.addAttribute("totalPages", clientPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageNumbers", pageNumbers);
        return "client/client-list";
    }

    @GetMapping("/info")
    public String getClientInfo(
            @RequestParam("clientId") int id,
            Model model){
        List<Project> projects = projectService.findProjectAssignedToClient(id);
        Client client = clientService.findById(id);
//        client.setProjects(projects);
        model.addAttribute("client", client);
        model.addAttribute("projects", projects);
        return "client/client-info";
    }

    @GetMapping("/info/projectConsultant")
    public String getProjectConsultant(@RequestParam("projectId") int id,
                                       Model model){
        ProjectConsultant consultant = consultantService.findByProjectId(id);
        model.addAttribute("consultant", consultant);
        return "client/consultant-info";
    }
}
