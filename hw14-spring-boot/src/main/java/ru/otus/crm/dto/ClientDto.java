package ru.otus.crm.dto;

import lombok.Getter;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ClientDto implements Serializable {
    private final Long id;
    private final String name;
    private final String address;
    private final List<String> phones;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();

        if (client.getAddress() != null) {
            this.address = client.getAddress().getStreet();
        } else {
            this.address = "";
        }

        if (client.getPhones() != null) {
            this.phones = client.getPhones().stream().map(Phone::getNumber).toList();
        } else {
            this.phones = new ArrayList<>();
        }
    }
}
