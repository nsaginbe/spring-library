package org.nurgisa.spring.dao;

import org.nurgisa.spring.mappers.BookMapper;
import org.nurgisa.spring.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooks() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?", new BookMapper(), id)
                .stream().findAny().orElse(null);
    }

    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book (title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void updateBook(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year = ? WHERE book_id = ?",
                book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", id);
    }

    public void deleteAllBooks() {
        jdbcTemplate.update("DELETE FROM Book");
    }

    public void freeBook(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = null WHERE book_id = ?", id);
    }

    public void assignBook(int id, int foreignId) {
        if (foreignId == 0) {
            jdbcTemplate.update("UPDATE Book SET person_id = null WHERE book_id = ?", id);
        }
        else {
            jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE book_id = ?", foreignId, id);
        }
    }
}
