package ru.otus.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    private Long id;

    private String number;
}
