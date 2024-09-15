package com.bernacki.hrapp.controller;

import com.bernacki.hrapp.dto.ClientDto;
import com.bernacki.hrapp.model.Client;
import com.bernacki.hrapp.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage/client")
public class ClientManageController {

    private ClientService clientService;

    @Autowired
    public ClientManageController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/add")
    public String addClient(Model model){
        model.addAttribute("client", new ClientDto());
        return "manage/client-add-form";
    }

    @PostMapping("/save")
    public String saveClient(@Valid @ModelAttribute("client") ClientDto clientDto,
                             BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors()){
            return "manage/client-add-form";
        }
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setAddress(clientDto.getAddress());
        clientService.save(client);
        return "redirect:/manage/";
    }
}
