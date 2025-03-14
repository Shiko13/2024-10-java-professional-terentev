package ru.otus.crm.dto;

import lombok.Getter;
import ru.otus.crm.model.Client;

import java.io.Serializable;

@Getter
public class ClientDto implements Serializable {
    private final Long id;
    private final String name;
    private final String address;
    private final String phone;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = client.getAddress().getStreet();
        this.phone = client.getPhone().getNumber();
    }
}
