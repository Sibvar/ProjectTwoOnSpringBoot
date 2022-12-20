package by.samuseu.springcourse.repositories;


import by.samuseu.springcourse.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findBookByBookName(String bookName);
    List<Book> findByBookNameStartsWith(String bookName);
}
