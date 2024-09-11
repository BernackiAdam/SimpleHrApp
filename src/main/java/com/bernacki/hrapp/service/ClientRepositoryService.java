package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Client;

import java.util.List;

public interface ClientRepositoryService {
    public List<Client> findAll();
    public Client findById(int id);
}
