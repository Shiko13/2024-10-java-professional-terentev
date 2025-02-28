package ru.otus.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientResultSetExtractor implements ResultSetExtractor<List<Client>> {

    @Override
    public List<Client> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Client> clientList = new ArrayList<>();
        Client currentClient = null;

        while (rs.next()) {
            long clientId = rs.getLong("client_id");
            String clientName = rs.getString("client_name");
            Address address = createAddress(rs);
            Phone phone = createPhone(rs);

            if (currentClient == null || currentClient.getId() != clientId) {
                if (currentClient != null) {
                    clientList.add(currentClient);
                }
                currentClient = new Client(clientId, clientName, address, phone);
            }
        }

        if (currentClient != null) {
            clientList.add(currentClient);
        }

        return clientList;
    }

    private Address createAddress(ResultSet rs) throws SQLException {
        long addressId = rs.getLong("address_id");
        return addressId != 0 ? new Address(addressId, rs.getString("street")) : null;
    }

    private Phone createPhone(ResultSet rs) throws SQLException {
        long phoneId = rs.getLong("phone_id");
        return phoneId != 0 ? new Phone(phoneId, rs.getString("phone_number")) : null;
    }
}
