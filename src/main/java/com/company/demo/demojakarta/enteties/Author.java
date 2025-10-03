package com.company.demo.demojakarta.enteties;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "author_name",  nullable = false)
    private String name;

    @Column
    private String address;

    @Column
    private String email;

    @Column(length = 20)
    private String phone;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Setter(AccessLevel.NONE)
    private Set<Book> books = new HashSet<>();


    @Override
    public String toString() {
        return "{ Author: " + this.name + "\n"
                + "  Address: " + this.address + "\n"
                + "  Email: " + this.email + "\n"
                + "  Phone: " + this.phone + " }\n";
    }
}
