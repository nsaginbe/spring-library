package org.nurgisa.spring.mappers;

import org.nurgisa.spring.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setId(rs.getInt("person_id"));
        person.setName(rs.getString("name"));
        person.setSurname(rs.getString("surname"));
        person.setYear(rs.getInt("year"));

        return person;
    }
}
