package ru.otus.mappers;

import ru.otus.jpql.crm.model.Phone;
import ru.otus.model.PhoneDto;

public class PhoneMapper {

    public static PhoneDto toDto(Phone phone) {
        return phone != null ? new PhoneDto(phone.getId(), phone.getNumber()) : null;
    }
}
