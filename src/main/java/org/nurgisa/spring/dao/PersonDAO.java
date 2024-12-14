package org.nurgisa.spring.dao;

import org.nurgisa.spring.mappers.BookMapper;
import org.nurgisa.spring.mappers.JustBookMapper;
import org.nurgisa.spring.mappers.PersonMapper;
import org.nurgisa.spring.models.Book;
import org.nurgisa.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople() {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person getPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = ?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void addPerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person (name, surname, year) VALUES (?, ?, ?)",
                person.getName(), person.getSurname(), person.getYear());
    }

    public void updatePerson(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name = ?, surname = ?, year = ? WHERE person_id = ?",
                person.getName(), person.getSurname(), person.getYear(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id = ?", id);
    }

    public void deletePeople() {
        jdbcTemplate.update("DELETE FROM Person");
    }

    public List<Book> getBooks(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM " +
                    "(SELECT Book.person_id, Book.title, Book.author, Book.year FROM Person INNER JOIN Book ON Person.person_id = Book.person_id) " +
                    "AS PB WHERE PB.person_id = ?", new Object[]{id}, new JustBookMapper());
    }
}
