package com.company.demo.demojakarta.enteties;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "publisher_name",  nullable = false)
    private String name;

    @Column
    private String address;

    @Column
    private String email;

    @Column(length = 20)
    private String phone;

    @OneToMany(mappedBy = "publisher", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Setter(AccessLevel.NONE)
    private Set<Book> books = new HashSet<>();

    @Override
    public String toString() {
        return "{ Publisher name: " + this.name + "\n"
                + "  Address: " + this.address + "\n"
                + "  Email: " + this.email + "\n"
                + "  Phone: " + this.phone + " }\n";
    }
}
