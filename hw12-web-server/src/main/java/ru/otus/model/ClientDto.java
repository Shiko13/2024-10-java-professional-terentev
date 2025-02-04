package ru.otus.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long id;

    private String name;

    private AddressDto address;

    private List<PhoneDto> phones;
}
