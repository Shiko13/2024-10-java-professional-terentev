package ru.otus.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    private Long id;

    private String name;

    @JsonProperty("address")
    @MappedCollection(idColumn = "address_id")
    private Address address;

    @JsonProperty("phone")
    @MappedCollection(idColumn = "phone_id")
    private Phone phone;
}