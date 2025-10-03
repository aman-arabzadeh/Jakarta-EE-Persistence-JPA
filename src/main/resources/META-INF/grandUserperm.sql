-- https://www.digitalocean.com/community/tutorials/how-to-create-a-new-user-and-grant-permissions-in-mysql
-- Best practise

CREATE USER 'bookstore'@'localhost' IDENTIFIED BY 'bookstorepassword';
GRANT ALL PRIVILEGES ON bookstore.* TO 'bookstore'@'localhost';
FLUSH PRIVILEGES;



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