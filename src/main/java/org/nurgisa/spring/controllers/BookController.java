package org.nurgisa.spring.controllers;

import org.nurgisa.spring.dao.BookDAO;
import org.nurgisa.spring.dao.PersonDAO;
import org.nurgisa.spring.models.Book;
import org.nurgisa.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String showBooks(Model model) {
        model.addAttribute("books", bookDAO.getBooks());
        return "books/show-books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.getBook(id);

        model.addAttribute("book", book);
        model.addAttribute("person", personDAO.getPerson(book.getForeignId()));
        model.addAttribute("people", personDAO.getPeople());

        return "books/show-book";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new-book";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") Book book, Model model) {
        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "books/edit-book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") Book book,
                             @RequestParam("actionType") String actionType) {

        switch (actionType) {
            case "edit":
                bookDAO.updateBook(id, book);
                break;
            case "free":
                bookDAO.freeBook(id);
                break;
            case "assign":
                bookDAO.assignBook(id, book.getForeignId());
        }

        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @DeleteMapping()
    public String deleteAllBooks() {
        bookDAO.deleteAllBooks();
        return "redirect:/books";
    }

}
