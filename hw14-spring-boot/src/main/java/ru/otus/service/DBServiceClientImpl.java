package ru.otus.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Client;
import ru.otus.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.List;

@Service
@AllArgsConstructor
public class DBServiceClientImpl implements DBServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(DBServiceClientImpl.class);
    private final ClientRepository clientRepository;
    private final TransactionManager transactionManager;

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(client);
            logger.info("Saved client {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public List<Client> findAll() {
        var allClients = clientRepository.findAll();
        logger.info("Find all clients {}", allClients);
        return allClients;
    }
}
