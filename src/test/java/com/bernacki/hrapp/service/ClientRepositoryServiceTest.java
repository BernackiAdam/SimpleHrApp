package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClientRepositoryServiceTest {

    @Autowired
    private ClientRepositoryService clientRepositoryService;

    @Test
    public void checkIfClientsAreLoaded(){
        List<Client> clients = clientRepositoryService.findAll();
        assertFalse(clients.isEmpty(), "Clients should not be empty");
        assertEquals("Company1", clients.stream()
                .map(Client::getName)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("List is empty")));
    }

    @Test
    public void checkIfClientIsLoadedById(){
        Client client = clientRepositoryService.findById(1);
        assertNotNull(client);

    }
}
