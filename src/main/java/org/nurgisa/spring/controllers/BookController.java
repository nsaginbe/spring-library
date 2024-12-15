package org.nurgisa.spring.controllers;

import org.nurgisa.spring.dao.BookDAO;
import org.nurgisa.spring.dao.PersonDAO;
import org.nurgisa.spring.models.Book;
import org.nurgisa.spring.models.Person;
import org.nurgisa.spring.utils.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
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
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/new-book";
        }

        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "books/edit-book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult,
                             @RequestParam("actionType") String actionType) {

        switch (actionType) {
            case "edit":
                bookValidator.validate(book, bindingResult);

                if (bindingResult.hasErrors()) {
                    return "books/edit-book";
                }

                bookDAO.updateBook(id, book);
                break;
            case "free":
                bookDAO.freeBook(id);
                break;
            case "assign":
                bookDAO.assignBook(id, book.getForeignId());
                break;
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
