package com.bernacki.hrapp.service;


import com.bernacki.hrapp.dao.ClientRepository;
import com.bernacki.hrapp.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientRepositoryServiceImpl implements ClientRepositoryService{

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    @Override
    public Client findById(int id) {
        Optional<Client> result = clientRepository.findById(id);
        Client client = null;
        if(result.isPresent()){
            client = result.get();
        }
        return client;
    }
}
