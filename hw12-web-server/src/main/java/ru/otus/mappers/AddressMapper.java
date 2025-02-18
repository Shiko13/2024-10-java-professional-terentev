package ru.otus.mappers;

import ru.otus.jpql.crm.model.Address;
import ru.otus.model.AddressDto;

public class AddressMapper {

    public static AddressDto toDto(Address address) {
        return address != null ? new AddressDto(address.getId(), address.getStreet()) : null;
    }

    public static Address toEntity(AddressDto addressDto) {
        return addressDto != null ? new Address(addressDto.getId(), addressDto.getStreet()) : null;
    }
}
