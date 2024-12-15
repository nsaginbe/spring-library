package org.nurgisa.spring.utils;

import org.nurgisa.spring.dao.BookDAO;
import org.nurgisa.spring.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class BookValidator implements Validator {

    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book targetBook = (Book) target;

        List<Book> books = bookDAO.getBooks();
        for (Book book : books) {
            if (book.getTitle().equals(targetBook.getTitle()) &&
                book.getAuthor().equals(targetBook.getAuthor()) &&
                book.getYear() == targetBook.getYear() &&
                book.getId() != targetBook.getId()
            ) {

                errors.reject("", "This book already exists!");
            }
        }

        if (targetBook.getYear() < 1 || targetBook.getYear() > 2024) {
            errors.rejectValue("year", "", "Year must be between 1 and 2024!");
        }
    }
}
