package org.nurgisa.spring.utils;

import org.nurgisa.spring.dao.PersonDAO;
import org.nurgisa.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person targetPerson = (Person) target;

        List<Person> people = personDAO.getPeople();
        for (Person person : people) {
            if (person.getName().equals(targetPerson.getName()) &&
                person.getSurname().equals(targetPerson.getSurname()) &&
                person.getId() != targetPerson.getId()) {

                errors.reject("", "This person already exists!");
            }
        }

        if (targetPerson.getYear() < 1900 || targetPerson.getYear() > 2024) {
            errors.rejectValue("year", "", "Year must be between 1900 and 2024!");
        }
    }
}
