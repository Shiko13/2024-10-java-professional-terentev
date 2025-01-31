package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "client")
@ToString(exclude = {"address", "phones"})
public class Client implements Cloneable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "client", orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setClient(this);
    }

    public <E> Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = new ArrayList<>();
        if (phones != null) {
            phones.forEach(this::addPhone);
        }
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        var clonedClient = new Client(this.id, this.name, null, null);

        if (this.address != null) {
            clonedClient.address = new Address(this.address.getId(), this.address.getStreet());
        }

        if (this.phones != null) {
            clonedClient.phones = this.phones.stream()
                    .map(phone -> new Phone(phone.getId(), phone.getNumber(), clonedClient))
                    .collect(Collectors.toList());
        }

        return clonedClient;
    }
}
