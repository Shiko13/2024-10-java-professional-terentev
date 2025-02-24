package ru.otus.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.crm.dto.ClientDto;
import ru.otus.crm.model.Client;
import ru.otus.service.DBServiceClient;

import java.util.List;

@RestController
@AllArgsConstructor
public class ClientRestController {

    private final DBServiceClient dbServiceClient;

    @GetMapping("/api/clients/all")
    public List<ClientDto> getAllClients() {
        List<Client> allClients = dbServiceClient.findAll();
        return allClients.stream().map(ClientDto::new).toList();
    }

    @PostMapping("/api/clients/")
    public ClientDto saveClient(@RequestBody Client client) {
        Client savedClient = dbServiceClient.saveClient(client);
        return new ClientDto(savedClient);
    }
}
