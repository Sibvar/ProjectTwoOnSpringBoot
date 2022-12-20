package by.samuseu.springcourse.services;


import by.samuseu.springcourse.models.Book;
import by.samuseu.springcourse.models.Person;
import by.samuseu.springcourse.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> sortBooksByYear(boolean sort) {
        return sort ? booksRepository.findAll(Sort.by("bookYear")) : findAll();
    }

    public List<Book> pagesOfBooks(int page, int booksPerPage) {
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> sortPagesOfBooks(boolean sort, int page, int booksPerPage) {
        return sort ? booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("bookYear"))).getContent() : pagesOfBooks(page, booksPerPage);
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public Optional<Book> findBookByBookName(String bookName) {
        return booksRepository.findBookByBookName(bookName);
    }

    public List<Book> findByBookNameStartsWith(String bookName) {
        return bookName != null ? booksRepository.findByBookNameStartsWith(bookName) : null;
    }


    @Transactional
    public void assignBook(int bookId, Person owner) {
        booksRepository.findById(bookId).orElse(null).setDateOfAssignment(new Date());
        booksRepository.findById(bookId).orElse(null).setOwner(owner);
    }

    public List<Book> expiredBook(List<Book> inputList) {
        List<Book> outBook = new ArrayList<>();
        for (Book book: inputList) {
            long milliseconds = new Date().getTime() - book.getDateOfAssignment().getTime();
            int days = (int) (milliseconds / (24 * 60 * 60 * 1000));
            if (days > 10) {
                book.setExpired(true);
            }
            outBook.add(book);
        }
        return outBook;
    }

    @Transactional
    public void releaseBook(int bookId) {
        Book book = booksRepository.findById(bookId).orElse(null);
        book.setOwner(null);
        book.setDateOfAssignment(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBookId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
}