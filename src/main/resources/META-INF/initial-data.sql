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

-- https://www.geeksforgeeks.org/dbms/difference-between-on-delete-cascade-and-on-delete-set-null-in-dbms/
-- https://www.w3schools.com/sql/sql_foreignkey.asp

