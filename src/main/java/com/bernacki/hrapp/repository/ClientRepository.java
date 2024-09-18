package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {


}
