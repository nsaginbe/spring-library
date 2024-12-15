package org.nurgisa.spring.models;

import javax.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty!")
    @Pattern(regexp = "[A-Z]\\w+", message = "Name must be in form: Bruno")
    private String name;

    @NotEmpty(message = "Surname should not be empty!")
    @Pattern(regexp = "[A-Z]\\w+", message = "Surname must be in form: Mars")
    private String surname;

//    @Min(value = 1900, message = "Year of birth should be over 1900!")
//    @Max(value = 2024, message = "Year of birth should be under 2024!")
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
