package ru.otus.mappers;

import ru.otus.jpql.crm.model.Client;
import ru.otus.model.ClientDto;

public class ClientMapper {

    public static ClientDto toDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setAddress(AddressMapper.toDto(client.getAddress()));
        clientDto.setPhones(client.getPhones() != null ? client.getPhones().stream()
                .map(PhoneMapper::toDto)
                .toList() : null);
        return clientDto;
    }
}
