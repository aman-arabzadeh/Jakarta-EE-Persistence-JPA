package com.company.demo.demojakarta.enteties;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPU");

        try (emf; EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Publishers
            Publisher harper = makePublisher("HarperCollins", "195 Broadway, New York, NY", "info@harpercollins.com", "000-0000");
            Publisher pan = makePublisher("Pan Books", "London, UK", "info@pan-books.com", "000-0001");
            Publisher wiley = makePublisher("Wiley", "Hoboken, NJ", "info@wiley.com", "000-0002");
            Publisher prentice = makePublisher("Prentice Hall", "NJ, USA", "info@prenticehall.com", "000-0003");
            Publisher addison = makePublisher("Addison-Wesley", "Boston, MA", "info@aw.com", "000-0004");

            em.persist(harper);
            em.persist(pan);
            em.persist(wiley);
            em.persist(prentice);
            em.persist(addison);

            // Authors
            Author coelho = makeAuthor("Paulo Coelho", "Brazil", "paulo@example.com", "111-1111");
            Author adams = makeAuthor("Douglas Adams", "UK", "douglas@example.com", "111-1112");
            Author horstmann = makeAuthor("Cay S. Horstmann", "USA", "cay@example.com", "111-1113");
            Author martin = makeAuthor("Robert C. Martin", "USA", "unclebob@example.com", "111-1114");
            Author bloch = makeAuthor("Joshua Bloch", "USA", "joshua@example.com", "111-1115");

            em.persist(coelho);
            em.persist(adams);
            em.persist(horstmann);
            em.persist(martin);
            em.persist(bloch);

            // Books (5 total)
            Book b1 = makeBook("The Alchemist", "A shepherd's journey of self-discovery.", "ISBN-0000000001");
            b1.setPublisher(harper);
            b1.addAuthor(coelho);

            Book b2 = makeBook("The Hitchhiker's Guide to the Galaxy", "Don't panic.", "ISBN-0000000002");
            b2.setPublisher(pan);
            b2.addAuthor(adams);

            Book b3 = makeBook("Big Java", "Intro to Java programming.", "ISBN-0000000003");
            b3.setPublisher(wiley);
            b3.addAuthor(horstmann);

            Book b4 = makeBook("Clean Code", "A Handbook of Agile Software Craftsmanship.", "ISBN-0000000004");
            b4.setPublisher(prentice);
            b4.addAuthor(martin);

            Book b5 = makeBook("Effective Java", "Best practices for Java.", "ISBN-0000000005");
            b5.setPublisher(addison);
            b5.addAuthor(bloch);
// Another book by Douglas Adams
            Book b7 = makeBook(
                    "The Restaurant at the End of the Universe",
                    "Sequel to Hitchhiker’s Guide—keep your towel handy.",
                    "ISBN-0000000007"
            );
            b7.setPublisher(pan);
            b7.addAuthor(adams);
            em.persist(b7);
            em.persist(b1);
            em.persist(b2);
            em.persist(b3);
            em.persist(b4);
            em.persist(b5);

            em.getTransaction().commit();
        }
    }

    private static Publisher makePublisher(String name, String address, String email, String phone) {
        Publisher p = new Publisher();
        p.setName(name);
        p.setAddress(address);
        p.setEmail(email);
        p.setPhone(phone);
        return p;
    }

    private static Author makeAuthor(String name, String address, String email, String phone) {
        Author a = new Author();
        a.setName(name);
        a.setAddress(address);
        a.setEmail(email);
        a.setPhone(phone);
        return a;
    }

    private static Book makeBook(String title, String desc, String isbn) {
        Book b = new Book();
        b.setTitle(title);
        b.setDescription(desc);
        b.setIsbn(isbn);
        return b;
    }
}
