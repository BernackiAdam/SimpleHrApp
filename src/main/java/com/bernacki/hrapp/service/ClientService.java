package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Client;

import java.util.List;

public interface ClientService {
    public List<Client> findAll();
    public Client findById(int id);
    public void save(Client client);
}
