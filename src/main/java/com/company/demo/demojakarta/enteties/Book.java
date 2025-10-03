package com.company.demo.demojakarta.enteties;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(length = 250, nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(length = 100, nullable = false, unique = true)
    private String isbn;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private Set<Author> authors = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    public void addAuthor(Author a) { authors.add(a); a.getBooks().add(this); }
    @Override
    public String toString() {
        return "{ Title: " + this.title + "\n"
                + "  Description: " + this.description + "\n"
                + "  ISBN: " + this.isbn + " }\n";
    }
}
