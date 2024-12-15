package org.nurgisa.spring.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Book {
    private int id;
    private int foreignId;

    @NotEmpty(message = "Title should not be empty!")
    @Pattern(regexp = "[A-Z].*", message = "Title must start with uppercase letter!")
    private String title;

    @NotEmpty(message = "Author should not be empty!")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Author must be in form: Abay Kunanbayev")
    private String author;

    @Min(value = 1, message = "Year of release should be real!")
    @Max(value = 2024, message = "Year of release should be under 2024!")
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getForeignId() {
        return foreignId;
    }

    public void setForeignId(int foreignId) {
        this.foreignId = foreignId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
