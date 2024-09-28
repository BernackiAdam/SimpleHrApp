package com.bernacki.hrapp.service;


import com.bernacki.hrapp.repository.ClientRepository;
import com.bernacki.hrapp.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

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

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Page<Client> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clientRepository.findAll(pageable);
    }
}
