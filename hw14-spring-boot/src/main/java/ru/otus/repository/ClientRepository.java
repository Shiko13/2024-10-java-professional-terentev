package ru.otus.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Override
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.address LEFT JOIN FETCH c.phones")
    List<Client> findAll();
}
