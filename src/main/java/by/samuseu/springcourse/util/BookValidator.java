package by.samuseu.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import by.samuseu.springcourse.services.BooksService;
import by.samuseu.springcourse.models.Book;

@Component
public class BookValidator implements Validator {

    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (booksService.findBookByBookName(book.getBookName()).isPresent()) {
            errors.rejectValue("bookName", "", "Книга с таким названием уже есть в базе");
        }
    }
}
