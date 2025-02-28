package ru.otus.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Override
    @Query(value = """
                SELECT c.id AS client_id,
                c.name AS client_name,
                a.address_id AS address_id,
                a.street AS street,
                p.phone_id AS phone_id,
                p.number AS phone_number from client c
                LEFT OUTER JOIN address a ON a.address_id = c.id
                LEFT OUTER JOIN phone p ON p.phone_id = c.id
                ORDER BY c.id
            """, resultSetExtractorClass = ClientResultSetExtractor.class)
    List<Client> findAll();
}
