package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.entity.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "/test.properties")
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEach(){
        jdbcTemplate.execute("ALTER TABLE clients ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.execute("INSERT INTO clients (name, address) VALUES('TestName1', 'TestAddress1')");
        jdbcTemplate.execute("INSERT INTO clients (name, address) VALUES('TestName2', 'TestAddress2')");
        jdbcTemplate.execute("INSERT INTO clients (name, address) VALUES('TestName3','TestAddress3')");

    }

    @AfterEach
    public void afterEach(){
        jdbcTemplate.execute("DELETE FROM clients");
    }

    @Test
    public void checkIfClientsAreLoaded(){
        List<Client> clientList = clientRepository.findAll();
        assertFalse(clientList.isEmpty());
        assertEquals(3, clientList.size());
        assertThat(clientList).extracting(Client::getName).containsExactly("TestName1", "TestName2", "TestName3");
    }

    @Test
    public void checkIfClientIsLoadedById(){
        Optional<Client> result = clientRepository.findById(1);
        Client client = result.orElse(null);
        assertNotNull(client);
        assertEquals("TestName1", client.getName());
    }


}
