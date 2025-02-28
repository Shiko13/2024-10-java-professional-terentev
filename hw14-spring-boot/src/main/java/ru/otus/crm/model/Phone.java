package ru.otus.crm.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phone")
public class Phone {

    @Id
    private Long phoneId;

    private String number;

    public Phone(String number) {
        this.number = number;
    }
}
