# Jakarta Persistence (JPA) Demo: Author↔Book (M:N) and Publisher→Book (1:*)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This demo illustrates how to use JPA for relational mapping between objects and their cardinalities.
It showcases Jakarta Persistence (JPA) relationships through the entities Author, Book, and Publisher:

Author ↔ Book (Many-to-Many)

Publisher → Book (One-to-Many)


For persistence, I used MySQL Workbench to create the database and tables,dedicated database user and granted it the necessary permissions  as best practise and for development I worked with IntelliJ IDEA.

```hsqldb
CREATE USER 'bookstore'@'localhost' IDENTIFIED BY 'bookstorepassword';
GRANT ALL PRIVILEGES ON bookstore.* TO 'bookstore'@'localhost';
FLUSH PRIVILEGES;
```
### Read More: [grant-permissions in MYSQL](https://www.digitalocean.com/community/tutorials/how-to-create-a-new-user-and-grant-permissions-in-mysql)
Also implemented a join function to combine useful data from the three entities (Author, Book, and Publisher).

---
# Useful links
- [Jakarta EE specifications persistence 3.2](https://jakarta.ee/specifications/persistence/3.2/)

- [Jakarta Persistence Query_Language](https://en.wikipedia.org/wiki/Jakarta_Persistence_Query_Language)

- [Modeling relationships many-to-many](https://learn.microsoft.com/en-us/ef/core/modeling/relationships/many-to-many)

- [difference-between-on-delete-cascade-and-on-delete-set-null-in-dbms](https://www.geeksforgeeks.org/dbms/difference-between-on-delete-cascade-and-on-delete-set-null-in-dbms/)

---

```hsqldb
publisher(id PK, name, address, email, phone)
book(id PK, title, description, isbn, publisher_id FK -> publisher.id)
author(id PK, name, address, email, phone)
author_book(author_id FK -> author.id, book_id FK -> book.id, PK(author_id, book_id))

```
---

```Java
// Publisher → Book (1:N)
@Entity
class Publisher {
    @OneToMany(mappedBy = "publisher", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Setter(AccessLevel.NONE)
    private Set<Book> books = new HashSet<>();
}

@Entity
class Book {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
}

// Author ↔ Book (M:N)
@Entity
class Author {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Setter(AccessLevel.NONE)
    private Set<Book> books = new HashSet<>();
}

@Entity
class Book {
    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private Set<Author> authors = new HashSet<>();
}
```

---
### My tables

```hsqldb
-- 1) Create database
CREATE DATABASE IF NOT EXISTS bookstore;
SHOW DATABASES;
USE bookstore;

-- 2) Publisher table
CREATE TABLE IF NOT EXISTS publisher (
                                         publisher_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         publisher_name VARCHAR(255) NOT NULL,
                                         address        VARCHAR(255),
                                         email          VARCHAR(255),
                                         phone          VARCHAR(20)
);

-- 3) Author table
CREATE TABLE IF NOT EXISTS author (
                                      author_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      author_name VARCHAR(255) NOT NULL,
                                      address     VARCHAR(255),
                                      email       VARCHAR(255),
                                      phone       VARCHAR(20)
);

-- 4) Book table
CREATE TABLE IF NOT EXISTS book (
                                    book_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    title        VARCHAR(250)  NOT NULL,
                                    description  VARCHAR(1000),
                                    isbn         VARCHAR(100) UNIQUE,
                                    publisher_id BIGINT,
                                    CONSTRAINT fk_book_publisher
                                        FOREIGN KEY (publisher_id)
                                            REFERENCES publisher(publisher_id)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE
);


-- 5) Author-Book join table (many-to-many)
CREATE TABLE IF NOT EXISTS author_book (
                                           author_id BIGINT NOT NULL,
                                           book_id   BIGINT NOT NULL,
                                           PRIMARY KEY (author_id, book_id),
                                           CONSTRAINT fk_author FOREIGN KEY (author_id)
                                               REFERENCES author(author_id)
                                               ON UPDATE CASCADE
                                               ON DELETE CASCADE,
                                           CONSTRAINT fk_book FOREIGN KEY (book_id)
                                               REFERENCES book(book_id)
                                               ON UPDATE CASCADE
                                               ON DELETE CASCADE
);

```
--- 
### Joining tables 
```hsqldb
-- Joining tables
select b.title,
       b.description,
       b.isbn,
       p.publisher_name,
       p.address,
       p.phone,
       p.email,
       a.author_name,
       a.address,
       a.phone,
       a.email
from book b
         LEFT JOIN bookstore.publisher p ON b.publisher_id = p.publisher_id
         LEFT JOIN bookstore.author_book ab on b.book_id = ab.book_id
         LEFT JOIN bookstore.author a on ab.author_id = a.author_id
```